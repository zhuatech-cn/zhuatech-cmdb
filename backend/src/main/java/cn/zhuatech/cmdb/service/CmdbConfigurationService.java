/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.service;

import cn.zhuatech.cmdb.model.*;
import cn.zhuatech.cmdb.repository.*;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CmdbConfigurationService {
    private final ConfigurationItemRepository items;
    private final ConfigurationRelationRepository relations;
    private final AuditLogRepository audits;

    public CmdbConfigurationService(ConfigurationItemRepository items,
            ConfigurationRelationRepository relations,AuditLogRepository audits){
        this.items=items;this.relations=relations;this.audits=audits;
    }

    public List<ConfigurationItem> list(){return items.findAllByOrderByUpdatedAtDesc();}

    @Transactional
    public ConfigurationItem register(ItemRequest request){
        if(items.findByCiCode(request.ciCode()).isPresent())throw conflict("配置项编码已存在");
        var item=items.save(new ConfigurationItem(request.ciCode(),request.ciType(),request.name(),
            request.organizationCode(),request.owner(),request.criticality(),request.serviceCode(),
            request.environment(),request.lastDiscoveredAt()));
        audit("登记配置项",request.ciCode(),request.name());return item;
    }

    @Transactional
    public ConfigurationItem refresh(String ciCode,DiscoveryRequest request){
        var item=get(ciCode);item.discovered(request.owner(),request.serviceCode(),request.discoveredAt());
        audit("刷新发现数据",ciCode,"责任人="+request.owner());return item;
    }

    @Transactional
    public ConfigurationItem retire(String ciCode){
        var item=get(ciCode);if("RETIRED".equals(item.getStatus()))throw conflict("配置项已经退役");
        item.retire();audit("退役配置项",ciCode,item.getName());return item;
    }

    @Transactional
    public ConfigurationRelation relate(RelationRequest request){
        get(request.sourceCiCode());get(request.targetCiCode());
        if(request.sourceCiCode().equals(request.targetCiCode()))throw bad("配置项不能依赖自身");
        if(relations.existsBySourceCiCodeAndTargetCiCodeAndRelationType(request.sourceCiCode(),
                request.targetCiCode(),request.relationType()))throw conflict("配置关系已存在");
        var relation=relations.save(new ConfigurationRelation(request.sourceCiCode(),
            request.targetCiCode(),request.relationType(),request.critical()));
        audit("建立配置关系",request.sourceCiCode(),request.relationType()+" -> "+request.targetCiCode());
        return relation;
    }

    public ImpactResult impact(String ciCode,int maxDepth){
        get(ciCode);if(maxDepth<1||maxDepth>8)throw bad("影响分析深度必须在1到8之间");
        Set<String> visited=new LinkedHashSet<>();Map<String,Integer> depths=new LinkedHashMap<>();
        Deque<String> queue=new ArrayDeque<>();queue.add(ciCode);depths.put(ciCode,0);
        int criticalLinks=0;
        while(!queue.isEmpty()){
            String current=queue.remove();int depth=depths.get(current);
            if(depth>=maxDepth)continue;
            for(var relation:relations.findByTargetCiCode(current)){
                if(relation.isCritical())criticalLinks++;
                if(visited.add(relation.getSourceCiCode())){
                    depths.put(relation.getSourceCiCode(),depth+1);queue.add(relation.getSourceCiCode());
                }
            }
        }
        long criticalItems=visited.stream().map(this::get)
            .filter(item->"CRITICAL".equals(item.getCriticality())).count();
        String risk=criticalItems>0||criticalLinks>1?"HIGH":visited.isEmpty()?"LOW":"MEDIUM";
        return new ImpactResult(ciCode,risk,visited.size(),criticalItems,criticalLinks,depths.entrySet().stream()
            .filter(entry->!entry.getKey().equals(ciCode)).map(entry->new AffectedItem(entry.getKey(),entry.getValue())).toList());
    }

    public List<ConfigurationItem> stale(int days){
        if(days<1||days>3650)throw bad("陈旧阈值必须在1到3650天之间");
        return items.findByLastDiscoveredAtBeforeOrderByLastDiscoveredAtAsc(LocalDateTime.now().minusDays(days));
    }

    public QualitySummary quality(){
        var all=items.findAll();Set<String> related=new HashSet<>();
        relations.findAll().forEach(r->{related.add(r.getSourceCiCode());related.add(r.getTargetCiCode());});
        long orphan=all.stream().filter(i->!related.contains(i.getCiCode())).count();
        long stale=all.stream().filter(i->i.getLastDiscoveredAt().isBefore(LocalDateTime.now().minusDays(30))).count();
        long withoutOwner=all.stream().filter(i->i.getOwner().isBlank()).count();
        return new QualitySummary(all.size(),relations.count(),orphan,stale,withoutOwner);
    }

    private ConfigurationItem get(String ciCode){
        return items.findByCiCode(ciCode).orElseThrow(()->
            new ResponseStatusException(HttpStatus.NOT_FOUND,"配置项不存在"));
    }
    private ResponseStatusException conflict(String message){return new ResponseStatusException(HttpStatus.CONFLICT,message);}
    private ResponseStatusException bad(String message){return new ResponseStatusException(HttpStatus.BAD_REQUEST,message);}
    private void audit(String action,String no,String detail){
        var auth=SecurityContextHolder.getContext().getAuthentication();
        audits.save(new AuditLog("CMDB",action,no,auth==null?"system":auth.getName(),detail));
    }

    public record ItemRequest(@NotBlank @Size(max=50) String ciCode,@NotBlank @Size(max=40) String ciType,
        @NotBlank @Size(max=120) String name,@NotBlank @Size(max=40) String organizationCode,
        @NotBlank @Size(max=60) String owner,@NotBlank @Pattern(regexp="LOW|MEDIUM|HIGH|CRITICAL") String criticality,
        @Size(max=60) String serviceCode,@NotBlank @Size(max=30) String environment,
        @NotNull LocalDateTime lastDiscoveredAt){}
    public record DiscoveryRequest(@NotBlank @Size(max=60) String owner,@Size(max=60) String serviceCode,
        @NotNull LocalDateTime discoveredAt){}
    public record RelationRequest(@NotBlank String sourceCiCode,@NotBlank String targetCiCode,
        @NotBlank @Size(max=40) String relationType,boolean critical){}
    public record AffectedItem(String ciCode,int depth){}
    public record ImpactResult(String sourceCiCode,String riskLevel,int affectedCount,long criticalItems,
        int criticalLinks,List<AffectedItem> affectedItems){}
    public record QualitySummary(long itemCount,long relationCount,long orphanCount,long staleCount,long withoutOwnerCount){}
}

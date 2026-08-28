/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.service;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Service;
import java.util.*;
@Service public class DomainDecisionService {
 public DecisionResult assess(DecisionRequest request) { double coverage=request.discoveredAssets()==0?100:request.managedAssets()*100d/request.discoveredAssets();int score=(int)Math.round((coverage+request.ownershipCoverage())/2);List<String> actions=new ArrayList<>();if(request.managedAssets()>request.discoveredAssets())throw new IllegalArgumentException("纳管资源数不能大于发现资源数");if(coverage<95){score-=20;actions.add("完成未纳管资源归并");}if(request.orphanRelationships()>0){score-=Math.min(20,request.orphanRelationships()*2);actions.add("修复孤立配置关系");}if(request.staleCis()>0){score-=Math.min(15,request.staleCis());actions.add("复核过期配置项");}if(request.duplicateDetected()){score-=35;actions.add("合并重复配置项并保留审计");}if(!request.criticalServiceMapped()){score-=35;actions.add("完成关键业务服务映射");}return result(score,actions,"HEALTHY","REMEDIATE","BLOCKED",Map.of("managedCoverage",coverage,"ownershipCoverage",request.ownershipCoverage(),"orphanRelationships",request.orphanRelationships(),"staleCis",request.staleCis())); }
 private DecisionResult result(int raw,List<String> actions,String good,String warn,String bad,Map<String,Object> metrics) { int score=Math.max(0,Math.min(100,raw));String decision=score>=80?good:score>=50?warn:bad;return new DecisionResult(decision,score,metrics,List.copyOf(actions)); }
 private DecisionResult riskResult(int raw,List<String> actions,String good,String warn,String bad,Map<String,Object> metrics) { int score=Math.max(0,Math.min(100,raw));String decision=score>=70?bad:score>=40?warn:good;return new DecisionResult(decision,score,metrics,List.copyOf(actions)); }
 public record DecisionRequest(
        @NotBlank String ciCode,
        @PositiveOrZero int discoveredAssets,
        @PositiveOrZero int managedAssets,
        @PositiveOrZero int orphanRelationships,
        @PositiveOrZero int staleCis,
        @DecimalMin("0") @DecimalMax("100") double ownershipCoverage,
        boolean duplicateDetected,
        boolean criticalServiceMapped) {}
 public record DecisionResult(String decision,int score,Map<String,Object> metrics,List<String> actions) {}
}

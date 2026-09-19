/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.domain;
import org.springframework.stereotype.Component;
import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Component
public class DomainCatalog {
    private final Map<String, WorkflowAction> actions = new LinkedHashMap<>();
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public DomainCatalog() {
        actions.put("SUBMIT", new WorkflowAction("SUBMIT", "提交配置核验", List.of("草稿"), "待核验", "OPERATOR"));
        actions.put("VERIFY", new WorkflowAction("VERIFY", "完成配置核验", List.of("待核验"), "已验证", "ADMIN"));
        actions.put("PUBLISH", new WorkflowAction("PUBLISH", "发布配置基线", List.of("已验证"), "已发布", "ADMIN"));
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String systemName() { return "知华科技企业配置管理数据库"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String scene() { return "配置项分类、自动发现、关系建模、服务映射、基线、变更校验、数据质量与审计"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String initialStatus() { return "草稿"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String partyLabel() { return "配置项/业务服务"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String amountLabel() { return "配置价值"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String quantityLabel() { return "配置项数量"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String dueLabel() { return "复核期限"; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public List<ModuleDefinition> modules() { return List.of(
            new ModuleDefinition("CI_CATALOG", "配置项台账", "维护硬件、软件、云资源、应用和业务服务配置项"),
            new ModuleDefinition("CI_CLASS", "模型与分类", "定义配置项类型、属性模板、唯一键和生命周期"),
            new ModuleDefinition("DISCOVERY", "自动发现", "接收扫描、云平台和监控工具发现结果并完成归并"),
            new ModuleDefinition("RELATIONSHIP", "关系管理", "维护依赖、部署、连接、承载和上下游关系"),
            new ModuleDefinition("SERVICE_MAPPING", "服务映射", "将配置项关联至业务服务、组织和服务负责人"),
            new ModuleDefinition("BASELINE", "配置基线", "保存批准基线、差异快照和合规检查结果"),
            new ModuleDefinition("CHANGE_CONTROL", "变更校验", "在变更前执行影响分析并在变更后核对配置"),
            new ModuleDefinition("DATA_QUALITY", "数据质量", "治理重复、孤立、过期、无责任人和属性缺失配置项"),
            new ModuleDefinition("AUDIT", "审计追踪", "记录发现、合并、变更、导出和管理员操作证据")
        ); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Map<String, WorkflowAction> actions() { return Collections.unmodifiableMap(actions); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record ModuleDefinition(String code,String name,String description) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record WorkflowAction(String code,String label,List<String> from,String to,String requiredRole) {}
}

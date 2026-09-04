/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationChangeImpactService {
    public Result assess(Request request) {
        var blockers = new ArrayList<String>();
        var actions = new ArrayList<String>();
        if (request.changeId() == null || request.changeId().isBlank()) blockers.add("变更编号不能为空");
        if (!request.ciExists()) blockers.add("目标配置项不存在");
        if (!request.criticalDependenciesAssessed()) blockers.add("关键依赖影响未评估");
        if (!request.backupVerified()) blockers.add("配置或数据备份未验证");
        if (!request.rollbackTested()) blockers.add("回滚方案未测试");
        if (!request.securityReviewed()) blockers.add("安全影响未评审");
        if (!request.changeManagerSeparated()) blockers.add("实施人与变更审批人未职责分离");
        if (!request.auditReady()) blockers.add("配置变更审计证据不完整");
        if (!request.relationshipGraphCurrent()) actions.add("刷新配置关系与依赖图");
        if (!request.ownerApproved()) actions.add("取得配置项责任人批准");
        if (!request.maintenanceWindowApproved()) actions.add("审批维护窗口");
        if (!request.monitoringReady()) actions.add("准备变更后监控与验证");
        var decision = !blockers.isEmpty() ? Decision.BLOCKED : actions.isEmpty() ? Decision.AUTHORIZE : Decision.ASSESS;
        return new Result(decision, List.copyOf(blockers), List.copyOf(actions));
    }

    public enum Decision { AUTHORIZE, ASSESS, BLOCKED }
    public record Request(String changeId, boolean ciExists, boolean relationshipGraphCurrent,
                          boolean criticalDependenciesAssessed, boolean ownerApproved,
                          boolean maintenanceWindowApproved, boolean backupVerified,
                          boolean rollbackTested, boolean securityReviewed,
                          boolean changeManagerSeparated, boolean monitoringReady, boolean auditReady) {}
    public record Result(Decision decision, List<String> blockers, List<String> actions) {}
}

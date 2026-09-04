/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class ConfigurationChangeImpactServiceTest {
    private final ConfigurationChangeImpactService service = new ConfigurationChangeImpactService();

    @Test void authorizesControlledConfigurationChange() {
        var result = service.assess(new ConfigurationChangeImpactService.Request("CHG-100", true, true, true,
                true, true, true, true, true, true, true, true));
        assertThat(result.decision()).isEqualTo(ConfigurationChangeImpactService.Decision.AUTHORIZE);
    }

    @Test void routesOperationalGapsToImpactAssessment() {
        var result = service.assess(new ConfigurationChangeImpactService.Request("CHG-101", true, false, true,
                false, false, true, true, true, true, false, true));
        assertThat(result.actions()).hasSize(4);
        assertThat(result.decision()).isEqualTo(ConfigurationChangeImpactService.Decision.ASSESS);
    }

    @Test void blocksUnsafeConfigurationChange() {
        var result = service.assess(new ConfigurationChangeImpactService.Request("", false, false, false,
                false, false, false, false, false, false, false, false));
        assertThat(result.blockers()).hasSize(8);
        assertThat(result.decision()).isEqualTo(ConfigurationChangeImpactService.Decision.BLOCKED);
    }
}

/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class ConfigurationChangeImpactServiceTest {
    private final ConfigurationChangeImpactService service = new ConfigurationChangeImpactService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void authorizesControlledConfigurationChange() {
        var result = service.assess(new ConfigurationChangeImpactService.Request("CHG-100", true, true, true,
                true, true, true, true, true, true, true, true));
        assertThat(result.decision()).isEqualTo(ConfigurationChangeImpactService.Decision.AUTHORIZE);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void routesOperationalGapsToImpactAssessment() {
        var result = service.assess(new ConfigurationChangeImpactService.Request("CHG-101", true, false, true,
                false, false, true, true, true, true, false, true));
        assertThat(result.actions()).hasSize(4);
        assertThat(result.decision()).isEqualTo(ConfigurationChangeImpactService.Decision.ASSESS);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void blocksUnsafeConfigurationChange() {
        var result = service.assess(new ConfigurationChangeImpactService.Request("", false, false, false,
                false, false, false, false, false, false, false, false));
        assertThat(result.blockers()).hasSize(8);
        assertThat(result.decision()).isEqualTo(ConfigurationChangeImpactService.Decision.BLOCKED);
    }
}

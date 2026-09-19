/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.controller;

import cn.zhuatech.cmdb.common.ApiResponse;
import cn.zhuatech.cmdb.service.ConfigurationChangeImpactService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/enterprise/cmdb")
public class ConfigurationChangeImpactController {
    private final ConfigurationChangeImpactService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public ConfigurationChangeImpactController(ConfigurationChangeImpactService service) { this.service = service; }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/configuration-change-impact")
    public ApiResponse<?> assess(@RequestBody ConfigurationChangeImpactService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}

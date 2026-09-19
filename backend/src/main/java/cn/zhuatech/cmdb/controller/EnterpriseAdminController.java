/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.controller;

import cn.zhuatech.cmdb.common.ApiResponse;
import cn.zhuatech.cmdb.model.EnterpriseControl;
import cn.zhuatech.cmdb.service.EnterpriseControlService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/admin/enterprise")
public class EnterpriseAdminController {
    private final EnterpriseControlService service;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public EnterpriseAdminController(EnterpriseControlService service) {
        this.service = service;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/controls/{id}/review")
    ApiResponse<EnterpriseControl> review(@PathVariable Long id,
            @Valid @RequestBody EnterpriseControlService.ReviewRequest request) {
        return ApiResponse.ok(service.review(id, request));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/controls/bulk-review")
    ApiResponse<EnterpriseControlService.BatchResult> bulkReview(
            @Valid @RequestBody EnterpriseControlService.BulkReviewRequest request) {
        return ApiResponse.ok(service.bulkReview(request));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/controls/{id}/sync")
    ApiResponse<EnterpriseControl> sync(@PathVariable Long id,
            @Valid @RequestBody EnterpriseControlService.SyncRequest request) {
        return ApiResponse.ok(service.sync(id, request));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PutMapping("/period-lock")
    ApiResponse<EnterpriseControlService.PeriodStatus> setPeriodLock(
            @Valid @RequestBody EnterpriseControlService.PeriodLockRequest request) {
        return ApiResponse.ok(service.setPeriodLock(request));
    }
}

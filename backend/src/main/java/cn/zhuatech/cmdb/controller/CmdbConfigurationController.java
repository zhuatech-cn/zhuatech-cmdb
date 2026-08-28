/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.controller;

import cn.zhuatech.cmdb.common.ApiResponse;
import cn.zhuatech.cmdb.model.*;
import cn.zhuatech.cmdb.service.CmdbConfigurationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cmdb")
public class CmdbConfigurationController {
    private final CmdbConfigurationService service;
    public CmdbConfigurationController(CmdbConfigurationService service){this.service=service;}

    @GetMapping("/configuration-items") ApiResponse<List<ConfigurationItem>> list(){return ApiResponse.ok(service.list());}
    @PostMapping("/configuration-items") ApiResponse<ConfigurationItem> register(@Valid @RequestBody CmdbConfigurationService.ItemRequest request){return ApiResponse.ok(service.register(request));}
    @PostMapping("/configuration-items/{ciCode}/discovery") ApiResponse<ConfigurationItem> refresh(@PathVariable String ciCode,@Valid @RequestBody CmdbConfigurationService.DiscoveryRequest request){return ApiResponse.ok(service.refresh(ciCode,request));}
    @PostMapping("/configuration-items/{ciCode}/retire") ApiResponse<ConfigurationItem> retire(@PathVariable String ciCode){return ApiResponse.ok(service.retire(ciCode));}
    @PostMapping("/relations") ApiResponse<ConfigurationRelation> relate(@Valid @RequestBody CmdbConfigurationService.RelationRequest request){return ApiResponse.ok(service.relate(request));}
    @GetMapping("/impact/{ciCode}") ApiResponse<CmdbConfigurationService.ImpactResult> impact(@PathVariable String ciCode,@RequestParam(defaultValue="4") int maxDepth){return ApiResponse.ok(service.impact(ciCode,maxDepth));}
    @PostMapping("/change-impact") ApiResponse<CmdbConfigurationService.ChangeImpactResult> assessChange(@Valid @RequestBody CmdbConfigurationService.ChangeImpactRequest request){return ApiResponse.ok(service.assessChange(request));}
    @GetMapping("/stale") ApiResponse<List<ConfigurationItem>> stale(@RequestParam(defaultValue="30") int days){return ApiResponse.ok(service.stale(days));}
    @GetMapping("/quality") ApiResponse<CmdbConfigurationService.QualitySummary> quality(){return ApiResponse.ok(service.quality());}
}

/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CmdbConfigurationApiTests {
    @Autowired MockMvc mvc;

    @Test
    void configurationGraphSupportsImpactAnalysisAndQualityMetrics() throws Exception {
        create("CI-DB-CORE","DATABASE","核心数据库","CRITICAL","2026-08-20T10:00:00");
        create("CI-APP-ORDER","APPLICATION","订单应用","HIGH","2026-08-20T11:00:00");
        create("CI-GW-EDGE","MIDDLEWARE","交易网关","HIGH","2026-08-20T12:00:00");

        relate("CI-APP-ORDER","CI-DB-CORE","DEPENDS_ON",true);
        relate("CI-GW-EDGE","CI-APP-ORDER","DEPENDS_ON",true);

        mvc.perform(get("/api/cmdb/impact/CI-DB-CORE").param("maxDepth","4")
                .with(httpBasic("operator","operator123")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.riskLevel").value("HIGH"))
            .andExpect(jsonPath("$.data.affectedCount").value(2))
            .andExpect(jsonPath("$.data.affectedItems.length()").value(2));

        mvc.perform(get("/api/cmdb/quality").with(httpBasic("operator","operator123")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.itemCount").value(3))
            .andExpect(jsonPath("$.data.relationCount").value(2));
    }

    @Test
    void discoveryRefreshStaleDetectionAndRetirementWork() throws Exception {
        create("CI-LEGACY-001","SERVER","遗留服务器","MEDIUM","2024-01-01T00:00:00");
        mvc.perform(get("/api/cmdb/stale").param("days","365")
                .with(httpBasic("operator","operator123")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[?(@.ciCode == 'CI-LEGACY-001')]").exists());

        mvc.perform(post("/api/cmdb/configuration-items/CI-LEGACY-001/discovery")
                .with(httpBasic("operator","operator123")).contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"owner":"基础架构组","serviceCode":"SVC-LEGACY","discoveredAt":"2026-08-28T08:00:00"}
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.owner").value("基础架构组"));

        mvc.perform(post("/api/cmdb/configuration-items/CI-LEGACY-001/retire")
                .with(httpBasic("operator","operator123")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.status").value("RETIRED"));
    }

    @Test
    void invalidAndDuplicateRelationsAreRejected() throws Exception {
        create("CI-VALID-A","APPLICATION","应用A","HIGH","2026-08-28T08:00:00");
        create("CI-VALID-B","DATABASE","数据库B","HIGH","2026-08-28T08:00:00");
        relate("CI-VALID-A","CI-VALID-B","DEPENDS_ON",false);
        mvc.perform(post("/api/cmdb/relations").with(httpBasic("operator","operator123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(relationBody("CI-VALID-A","CI-VALID-B","DEPENDS_ON",false)))
            .andExpect(status().isConflict());
        mvc.perform(post("/api/cmdb/relations").with(httpBasic("operator","operator123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(relationBody("CI-VALID-A","CI-VALID-A","DEPENDS_ON",false)))
            .andExpect(status().isBadRequest());
    }

    private void create(String code,String type,String name,String criticality,String discoveredAt) throws Exception {
        mvc.perform(post("/api/cmdb/configuration-items").with(httpBasic("operator","operator123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ciCode\":\""+code+"\",\"ciType\":\""+type+"\",\"name\":\""+name
                    +"\",\"organizationCode\":\"ZH-SH\",\"owner\":\"平台组\",\"criticality\":\""
                    +criticality+"\",\"serviceCode\":\"SVC-TRADE\",\"environment\":\"PROD\","
                    +"\"lastDiscoveredAt\":\""+discoveredAt+"\"}"))
            .andExpect(status().isOk());
    }

    private void relate(String source,String target,String type,boolean critical) throws Exception {
        mvc.perform(post("/api/cmdb/relations").with(httpBasic("operator","operator123"))
                .contentType(MediaType.APPLICATION_JSON).content(relationBody(source,target,type,critical)))
            .andExpect(status().isOk());
    }

    private String relationBody(String source,String target,String type,boolean critical){
        return "{\"sourceCiCode\":\""+source+"\",\"targetCiCode\":\""+target
            +"\",\"relationType\":\""+type+"\",\"critical\":"+critical+"}";
    }
}

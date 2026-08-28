/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.repository;

import cn.zhuatech.cmdb.model.ConfigurationRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConfigurationRelationRepository extends JpaRepository<ConfigurationRelation,Long> {
    boolean existsBySourceCiCodeAndTargetCiCodeAndRelationType(String source,String target,String type);
    List<ConfigurationRelation> findByTargetCiCode(String targetCiCode);
}

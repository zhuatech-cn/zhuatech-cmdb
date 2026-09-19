/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.repository;

import cn.zhuatech.cmdb.model.ConfigurationRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface ConfigurationRelationRepository extends JpaRepository<ConfigurationRelation,Long> {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    boolean existsBySourceCiCodeAndTargetCiCodeAndRelationType(String source,String target,String type);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    List<ConfigurationRelation> findBySourceCiCode(String sourceCiCode);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    List<ConfigurationRelation> findByTargetCiCode(String targetCiCode);
}

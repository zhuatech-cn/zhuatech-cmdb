/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.repository;

import cn.zhuatech.cmdb.model.ConfigurationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface ConfigurationItemRepository extends JpaRepository<ConfigurationItem,Long> {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    Optional<ConfigurationItem> findByCiCode(String ciCode);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    List<ConfigurationItem> findAllByOrderByUpdatedAtDesc();
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    List<ConfigurationItem> findByLastDiscoveredAtBeforeOrderByLastDiscoveredAtAsc(LocalDateTime threshold);
}

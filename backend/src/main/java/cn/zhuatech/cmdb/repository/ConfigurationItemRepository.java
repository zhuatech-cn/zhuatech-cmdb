/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.repository;

import cn.zhuatech.cmdb.model.ConfigurationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.*;

public interface ConfigurationItemRepository extends JpaRepository<ConfigurationItem,Long> {
    Optional<ConfigurationItem> findByCiCode(String ciCode);
    List<ConfigurationItem> findAllByOrderByUpdatedAtDesc();
    List<ConfigurationItem> findByLastDiscoveredAtBeforeOrderByLastDiscoveredAtAsc(LocalDateTime threshold);
}

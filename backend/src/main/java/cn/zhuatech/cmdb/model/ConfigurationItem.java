/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity
@Table(name="configuration_items", uniqueConstraints=@UniqueConstraint(columnNames="ciCode"))
public class ConfigurationItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=50) private String ciCode;
    @Column(nullable=false,length=40) private String ciType;
    @Column(nullable=false,length=120) private String name;
    @Column(nullable=false,length=40) private String organizationCode;
    @Column(nullable=false,length=60) private String owner;
    @Column(nullable=false,length=20) private String criticality;
    @Column(nullable=false,length=24) private String status;
    @Column(length=60) private String serviceCode;
    @Column(nullable=false,length=30) private String environment;
    @Column(nullable=false) private LocalDateTime lastDiscoveredAt;
    @Version private long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected ConfigurationItem() {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public ConfigurationItem(String ciCode,String ciType,String name,String organizationCode,String owner,
            String criticality,String serviceCode,String environment,LocalDateTime lastDiscoveredAt) {
        this.ciCode=ciCode;this.ciType=ciType;this.name=name;this.organizationCode=organizationCode;
        this.owner=owner;this.criticality=criticality;this.serviceCode=serviceCode;
        this.environment=environment;this.lastDiscoveredAt=lastDiscoveredAt;this.status="ACTIVE";
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PrePersist void created(){createdAt=updatedAt=LocalDateTime.now();}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PreUpdate void updated(){updatedAt=LocalDateTime.now();}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public void discovered(String owner,String serviceCode,LocalDateTime at){
        this.owner=owner;this.serviceCode=serviceCode;this.lastDiscoveredAt=at;this.status="ACTIVE";
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public void retire(){this.status="RETIRED";}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Long getId(){return id;} /**
                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                     */
public String getCiCode(){return ciCode;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getCiType(){return ciType;} /**
                                               * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                               */
public String getName(){return name;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getOrganizationCode(){return organizationCode;} /**
                                                                   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                   */
public String getOwner(){return owner;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getCriticality(){return criticality;} /**
                                                         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                         */
public String getStatus(){return status;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getServiceCode(){return serviceCode;} /**
                                                         * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                         */
public String getEnvironment(){return environment;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getLastDiscoveredAt(){return lastDiscoveredAt;} /**
                                                                          * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                          */
public long getVersion(){return version;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getCreatedAt(){return createdAt;} /**
                                                            * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                            */
public LocalDateTime getUpdatedAt(){return updatedAt;}
}

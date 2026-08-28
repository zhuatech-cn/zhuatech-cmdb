/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    protected ConfigurationItem() {}

    public ConfigurationItem(String ciCode,String ciType,String name,String organizationCode,String owner,
            String criticality,String serviceCode,String environment,LocalDateTime lastDiscoveredAt) {
        this.ciCode=ciCode;this.ciType=ciType;this.name=name;this.organizationCode=organizationCode;
        this.owner=owner;this.criticality=criticality;this.serviceCode=serviceCode;
        this.environment=environment;this.lastDiscoveredAt=lastDiscoveredAt;this.status="ACTIVE";
    }

    @PrePersist void created(){createdAt=updatedAt=LocalDateTime.now();}
    @PreUpdate void updated(){updatedAt=LocalDateTime.now();}
    public void discovered(String owner,String serviceCode,LocalDateTime at){
        this.owner=owner;this.serviceCode=serviceCode;this.lastDiscoveredAt=at;this.status="ACTIVE";
    }
    public void retire(){this.status="RETIRED";}

    public Long getId(){return id;} public String getCiCode(){return ciCode;}
    public String getCiType(){return ciType;} public String getName(){return name;}
    public String getOrganizationCode(){return organizationCode;} public String getOwner(){return owner;}
    public String getCriticality(){return criticality;} public String getStatus(){return status;}
    public String getServiceCode(){return serviceCode;} public String getEnvironment(){return environment;}
    public LocalDateTime getLastDiscoveredAt(){return lastDiscoveredAt;} public long getVersion(){return version;}
    public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
}

/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="configuration_relations",uniqueConstraints=@UniqueConstraint(
    columnNames={"sourceCiCode","targetCiCode","relationType"}))
public class ConfigurationRelation {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=50) private String sourceCiCode;
    @Column(nullable=false,length=50) private String targetCiCode;
    @Column(nullable=false,length=40) private String relationType;
    @Column(nullable=false) private boolean critical;
    private LocalDateTime createdAt;

    protected ConfigurationRelation() {}
    public ConfigurationRelation(String source,String target,String type,boolean critical){
        this.sourceCiCode=source;this.targetCiCode=target;this.relationType=type;this.critical=critical;
    }
    @PrePersist void created(){createdAt=LocalDateTime.now();}
    public Long getId(){return id;} public String getSourceCiCode(){return sourceCiCode;}
    public String getTargetCiCode(){return targetCiCode;} public String getRelationType(){return relationType;}
    public boolean isCritical(){return critical;} public LocalDateTime getCreatedAt(){return createdAt;}
}

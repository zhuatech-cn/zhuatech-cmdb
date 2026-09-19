/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.cmdb.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
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

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected ConfigurationRelation() {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public ConfigurationRelation(String source,String target,String type,boolean critical){
        this.sourceCiCode=source;this.targetCiCode=target;this.relationType=type;this.critical=critical;
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PrePersist void created(){createdAt=LocalDateTime.now();}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Long getId(){return id;} /**
                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                     */
public String getSourceCiCode(){return sourceCiCode;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getTargetCiCode(){return targetCiCode;} /**
                                                           * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                           */
public String getRelationType(){return relationType;}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public boolean isCritical(){return critical;} /**
                                                   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                   */
public LocalDateTime getCreatedAt(){return createdAt;}
}

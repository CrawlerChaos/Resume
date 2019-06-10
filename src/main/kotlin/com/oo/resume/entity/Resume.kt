package com.oo.resume.entity

import org.hibernate.annotations.Cascade
import javax.persistence.*

/**
 *  yangchao yangchao
 *  cd.uestc.superyong@gmail.com  cd.uestc.superyoung@gmail.com
 *     2019-05-21 16:34
 *
 */
@Entity
data class Resume @JvmOverloads constructor(

        @Column(nullable = false)
        var shortLink: String,//简历短链

        @ManyToOne
        @JoinColumn(name = "account_id")
        var account: Account,//基本信息

        @OneToOne
        @JoinColumn(name = "base_info_id")
        var baseInfo: BaseInfo,

        @Column(nullable = true, columnDefinition = "text")
        var synopsis: String? = null,//个人简介

        @Column(nullable = false)
        var exeprience: Int,//多少年经验

        @OneToOne
        @JoinColumn(name = "company_id", nullable = true)
        var company: Company? = null,//当前公司

        @Column(nullable = true)
        var jobLabel: String? = null,//职位描述


        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "resume_id")
        var language: List<Language>? = null,//语言

        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "resume_id")
        var technique: List<Technique>? = null,//技术

        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "resume_id")
        var experiences: List<Experience>? = null,//工作经验

        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "resume_id")
        var education: List<Education>? = null,//教育经历

        @Id
        @GeneratedValue
        var id: Long = 0
)
package com.oo.resume.entity

import javax.persistence.*

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-22 15:17
 *  工作经历
 */
@Entity
data class Experience @JvmOverloads constructor(
        @ManyToOne
        @JoinColumn
        var company: Company,//公司

        @Column(nullable = false, columnDefinition = "text")
        var sketch: String,//概述

        @Column(nullable = false, columnDefinition = "text")
        var jobContent: String,//工作内容

        @Column(nullable = false)
        var start: String,//开始时间

        @Column(nullable = true)
        var end: String? = null,//结束时间

        @Column(nullable = true, length = 32)
        var station: String? = null, //岗位

        @OneToMany
        @JoinTable(//关联表
                name = "expreience_labels",
                joinColumns = [JoinColumn(name = "expreience_id")],
                inverseJoinColumns = [JoinColumn(name = "label_id")]
        )
        var labels: List<Label>? = null,//标签

        @Column(nullable = true)
        var opus: String? = null//作品link

) : BaseEntity()
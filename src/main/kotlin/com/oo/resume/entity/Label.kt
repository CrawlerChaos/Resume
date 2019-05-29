package com.oo.resume.entity

import javax.persistence.*

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-24 17:29
 *
 */
@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["name"])])
data class Label @JvmOverloads constructor(
        @Column(nullable = false, length = 16)
        val name: String,//名称

        @Column(nullable = true)
        var description: String? = null,//描述

        @Id
        @GeneratedValue
        var id: Long = 0

)
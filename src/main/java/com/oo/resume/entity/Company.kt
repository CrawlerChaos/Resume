package com.oo.resume.entity

import javax.persistence.*

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-22 15:37
 *
 */
@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["name"])])
data class Company @JvmOverloads constructor(
        @Column(nullable = false, length = 32)
        var name: String,//公司名

        @Column(nullable = true)
        var icon: String? = null,//公司icon

        @Column(nullable = true)
        var homepage: String? = null,//公司主页

        @Id
        @GeneratedValue
        var id: Long = 0

)
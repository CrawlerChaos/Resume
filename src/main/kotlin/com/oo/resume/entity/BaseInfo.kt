package com.oo.resume.entity

import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-21 17:19
 *
 */
@Entity
@Table
data class BaseInfo @JvmOverloads constructor(
        @Column(nullable = false, length = 16)
        var name: String,//名字

        @Column(nullable = false, length = 16)
        var phone: String,//电话

        @Column(nullable = true)
        var age: Int? = null,//年龄

        @Column(nullable = true)
        var sex: Int? = null,//性别 0:男 1:女

        @Column(nullable = true, length = 32)
        var email: String? = null,//邮件

        @Column(nullable = true)
        var avatar: String? = null,//头像

        @Id
        @GeneratedValue
        var id: Long = 0
)
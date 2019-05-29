package com.oo.resume.entity

import org.hibernate.annotations.GenericGenerator
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-21 17:19
 *
 */
@Entity
data class User @JvmOverloads constructor(
        @Column(nullable = false, length = 32)
        var name: String,//名字

        @Column(nullable = false)
        var age: Int,//年龄

        @Column(nullable = false, length = 32)
        var phone: String,//电话

        @Column(nullable = true, length = 32)
        var email: String? = null,//邮件

        @Column(nullable = true)
        var avatar: String? = null,//头像

        @Id
        @GenericGenerator(name="idGenerator", strategy="uuid")
        @GeneratedValue(generator="idGenerator")
        @Column(length = 32)
        var id: String? = null
)
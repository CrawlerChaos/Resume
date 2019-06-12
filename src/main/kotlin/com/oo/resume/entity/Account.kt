package com.oo.resume.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-21 17:19
 *
 */
@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["phone", "session_user"])])
data class Account @JvmOverloads constructor(
        @Column(nullable = false, length = 16)
        var phone: String,//电话

        @Column(nullable = false, length = 16)
        var password: String,//密码

        @Column(nullable = false, length = 16)
        var name: String,//名字

        @Column(nullable = true)
        var age: Int? = null,//年龄

        @Column(nullable = true)
        var sex: Int? = null,//性别 0:男 1:女

        @Column(nullable = true)
        var avatar: String? = null,//头像

        @Column(length = 36)
        var session_key: String? = null,//会话Key

        @Column(length = 36)
        var session_user: String? = null//会话User

) : BaseEntity()
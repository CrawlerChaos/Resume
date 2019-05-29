package com.oo.resume.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-21 17:40
 *
 */
@Entity
data class Technique @JvmOverloads constructor(
        @Column(nullable = false)
        var title: String,//名字

        @Column(nullable = true)
        var percent: Float? = 1f,//熟练度

        @Column(nullable = true)
        var description: String? = null,//描述

        @Id
        @GeneratedValue
        var id: Long = 0
)
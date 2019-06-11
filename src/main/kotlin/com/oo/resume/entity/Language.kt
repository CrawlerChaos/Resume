package com.oo.resume.entity

import javax.persistence.Column
import javax.persistence.Entity

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-28 16:27
 *
 */
@Entity
data class Language @JvmOverloads constructor(
        @Column(nullable = false)
        var title: String,//名字

        @Column(nullable = true)
        var percent: Float? = 1f,//熟练度

        @Column(nullable = true)
        var description: String? = null//描述

) : BaseEntity()
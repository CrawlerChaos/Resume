package com.oo.resume.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

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
        var name: String,//名称

        @Column(nullable = true)
        var description: String? = null//描述

) : BaseEntity()
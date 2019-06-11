package com.oo.resume.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-22 15:37
 *
 */
@Entity
data class Education @JvmOverloads constructor(
        @ManyToOne
        @JoinColumn
        var school: School,//学校

        @Column(nullable = false, length = 32)
        var record: String,//学历

        @Column(nullable = false, length = 32)
        var start: String,//开始时间

        @Column(nullable = true, length = 32)
        var end: String?,//结束时间

        @Column(nullable = true, length = 32)
        var major: String? = null//专业

) : BaseEntity()
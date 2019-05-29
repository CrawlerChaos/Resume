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
data class School @JvmOverloads constructor(
        @Column(nullable = false, length = 32)
        var name: String,//学校名

        @Column(nullable = true)
        var icon: String? = null,//学校icon

        @OneToMany
        @JoinTable(
                name = "school_labels",
                joinColumns = [JoinColumn(name = "school_id")],
                inverseJoinColumns = [JoinColumn(name = "label_id")]
        )
        var labels: List<Label>? = null,//标签

        @Id
        @GeneratedValue
        var id: Long = 0


)
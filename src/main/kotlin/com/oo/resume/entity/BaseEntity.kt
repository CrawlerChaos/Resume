package com.oo.resume.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-11 11:34
 *
 */
@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class BaseEntity() {
    @Id
    @GeneratedValue
    var id: Long = 0

    @CreatedDate
    var createTime: Long = 0

    @LastModifiedDate
    var updateTime: Long = 0
}


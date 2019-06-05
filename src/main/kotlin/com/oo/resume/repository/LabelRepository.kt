package com.oo.resume.repository

import com.oo.resume.entity.Label

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-05-24 15:13
 */
interface LabelRepository : JpaRepository<Label, Long> {
    @Query("from Label label where label.name=:name")
    fun findLabelByName(@Param("name") name: String): Label?
}

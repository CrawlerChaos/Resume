package com.oo.resume.repository

import com.oo.resume.entity.Resume

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-05-24 15:13
 */
interface ResumeRepository : JpaRepository<Resume, Long> {


    @Query("from Resume resume where resume.account.id=:id")
    fun findResumeByAccountID(@Param("id") id: Long): List<Resume>?

    @Query("from Resume resume where resume.shortLink=:shortLink")
    fun findResumeByShortLink(@Param("shortLink") shortLink: String): Resume?

}

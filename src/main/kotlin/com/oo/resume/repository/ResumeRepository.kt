package com.oo.resume.repository

import com.oo.resume.entity.Resume

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-05-24 15:13
 */
interface ResumeRepository : JpaRepository<Resume, Long> {


    @Query("from Resume resume where resume.account.id=:accountID")
    fun findAllByAccountID(@Param("accountID") accountID: Long): List<Resume>?

    @Query("from Resume resume where resume.shortLink=:shortLink")
    fun findByShortLink(@Param("shortLink") shortLink: String): Resume?

    @Query("delete from Resume resume where resume.id=:id and resume.account.id=:accountID")
    @Modifying
    fun deleteResume(@Param("id") id: Long, @Param("accountID") accountID: Long): Resume?

    @Query("from Resume resume where resume.id=:id and resume.account.id=:accountID")
    fun findResumeByID(@Param("id") id: Long, @Param("accountID") accountID: Long): Resume?

}

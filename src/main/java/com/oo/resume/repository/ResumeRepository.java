package com.oo.resume.repository;

import com.oo.resume.entity.Resume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-05-24 15:13
 */
public interface ResumeRepository extends JpaRepository<Resume, Long> {


    @Query("from Resume resume where resume.user.id=:id")
    Resume findResumeByUserID(@Param("id") String id);

    @Query("from Resume resume where resume.shortLink=:shortLink")
    Resume findResumeByShortLink(@Param("shortLink") String shortLink);

}

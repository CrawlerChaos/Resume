package com.oo.resume.repository;

import com.oo.resume.entity.School;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-05-24 15:13
 */
public interface SchoolRepository extends JpaRepository<School, Long> {


    @Query("from School school where school.name=:name")
    School findSchoolByName(@Param("name") String name);

}

package com.oo.resume.repository;

import com.oo.resume.entity.Company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-05-24 15:13
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {


    @Query("from Company company where company.name=:name")
    Company findCompanyByName(@Param("name") String name);

}

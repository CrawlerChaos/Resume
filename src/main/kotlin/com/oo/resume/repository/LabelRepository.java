package com.oo.resume.repository;

import com.oo.resume.entity.Label;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-05-24 15:13
 */
public interface LabelRepository extends JpaRepository<Label, Long> {
    @Query("from Label label where label.name=:name")
    Label findLabelByName(@Param("name") String name);
}

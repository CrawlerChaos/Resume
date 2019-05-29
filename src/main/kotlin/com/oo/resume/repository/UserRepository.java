package com.oo.resume.repository;

import com.oo.resume.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-05-24 15:13
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User user where user.name=:name")
    User findUserByName(@Param("name") String name);

    @Query("from User user where user.id=:uuid")
    User findUserByUUID(@Param("uuid") String uuid);
}

package com.oo.resume.repository

import com.oo.resume.entity.User

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-05-24 15:13
 */
interface UserRepository : JpaRepository<User, Long> {

    @Query("from User user where user.name=:name")
    fun findUserByName(@Param("name") name: String): User

    @Query("from User user where user.id=:uuid")
    fun findUserByUUID(@Param("uuid") uuid: String): User

    @Modifying
    @Transactional
    @Query("update User eUser set eUser.name = CASE WHEN :#{#user.name} IS NULL THEN eUser.name ELSE :#{#user.name} END ,eUser.age = CASE WHEN :#{#user.age} IS NULL THEN eUser.age ELSE :#{user.age} END ,eUser.sex = CASE WHEN :#{#user.sex} IS NULL THEN eUser.sex ELSE :#{#user.sex} END ,eUser.avatar = CASE WHEN :#{#user.avatar} IS NULL THEN eUser.avatar ELSE :#{user.avatar} END ,eUser.email = CASE WHEN :#{#user.email} IS NULL THEN eUser.avatar ELSE :#{#user.email} END where eUser.id = :#{#user.id}")
    fun update(@Param("user") user: User): Int
}

package com.oo.resume.repository

import com.oo.resume.entity.Account
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
interface AccountRepository : JpaRepository<Account, Long> {

    @Query("from Account user where user.session_user=:session_user")
    fun findBySessionUser(@Param("session_user") session_user: String): Account?

    @Query("from Account user where user.phone=:phone")
    fun findByPhone(@Param("phone") phone: String): Account?

//    @Modifying
//    @Query("update Account eUser set " +
//            "eUser.name = CASE WHEN :name IS NULL THEN eUser.name ELSE :name END ," +
//            "eUser.age = CASE WHEN :age IS NULL THEN eUser.age ELSE :age END ," +
//            "eUser.sex = CASE WHEN :sex IS NULL THEN eUser.sex ELSE :sex END ," +
//            "eUser.avatar = CASE WHEN :avatar IS NULL THEN eUser.avatar ELSE :avatar END " +
//            "where eUser.id = :id")
//    fun update(@Param("id") id: Long?, @Param("name") name: String?, @Param("age") age: Int?,
//               @Param("sex") sex: Int?, @Param("avatar") avatar: String?)
}

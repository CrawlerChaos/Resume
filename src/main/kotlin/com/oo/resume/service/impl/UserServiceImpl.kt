package com.oo.resume.service.impl

import com.oo.resume.entity.User
import com.oo.resume.repository.UserRepository
import com.oo.resume.service.interf.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-02 15:20
 *
 */
@Service
open class UserServiceImpl : IUserService {


    @Autowired
    lateinit var userRepo: UserRepository

    override fun save(user: User): User {
        return userRepo.save(user)
    }

    @Transactional
    override fun update(user: User): User {
        userRepo.update(user.id, user.name, user.age, user.sex, user.avatar, user.email)
        return user
    }

    override fun getById(id: Long): User? {
        try {
            return userRepo.getOne(id)

        } catch (e: EmptyResultDataAccessException) {

        }
        return null
    }

    override fun getByPhone(phone: String): User? {

        try {
            return userRepo.findUserByPhone(phone)
        } catch (e: EmptyResultDataAccessException) {

        }
        return null
    }

}
package com.oo.resume.service.interf

import com.oo.resume.entity.User

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-28 18:06
 *
 */
interface IUserService {
    fun save(user: User): User
    fun update(user: User): User
}
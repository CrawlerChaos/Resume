package com.oo.resume.service.interf

import com.oo.resume.entity.Account

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-28 18:06
 *
 */
interface IAccountService {
    fun save(account: Account): Account
    fun getById(id: Long): Account?
    fun getBySessionUser(sessionUser: String): Account?
    fun getByPhone(phone: String): Account?
}
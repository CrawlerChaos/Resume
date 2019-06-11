package com.oo.resume.service.impl

import com.oo.resume.entity.Account
import com.oo.resume.repository.AccountRepository
import com.oo.resume.service.interf.IAccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-02 15:20
 *
 */
@Service
open class AccountServiceImpl : IAccountService {


    @Autowired
    lateinit var accountRepo: AccountRepository

    override fun save(account: Account): Account {
        return accountRepo.save(account)
    }

    @Transactional
    override fun update(account: Account): Account {
        accountRepo.update(account.id, account.name, account.age, account.sex, account.avatar)
        return account
    }

    override fun getById(id: Long): Account? {
        return accountRepo.findByIdOrNull(id)
    }

    override fun getBySessionUser(sessionUser: String): Account? {
        return accountRepo.findAccountBySessionUser(sessionUser)
    }

    override fun getByPhone(phone: String): Account? {
        return accountRepo.findAccountByPhone(phone)
    }

}
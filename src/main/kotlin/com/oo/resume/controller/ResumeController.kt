package com.oo.resume.controller

import com.oo.resume.entity.*
import com.oo.resume.exception.AuthorityError
import com.oo.resume.data.header.HeaderConst
import com.oo.resume.data.path.UrlConst
import com.oo.resume.data.response.ResumeDTO
import com.oo.resume.service.interf.IAccountService
import com.oo.resume.service.interf.IResumeService
import com.oo.resume.util.BeanCovertor
import org.modelmapper.TypeToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.ArrayList

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-29 10:56
 *
 */
@RestController
@RequestMapping(UrlConst.RESUME_PREFIX)
class ResumeController {

    @Autowired
    @Lazy
    lateinit var resumeService: IResumeService

    @Autowired
    @Lazy
    lateinit var accountService: IAccountService


    @GetMapping(UrlConst.RESUME_LIST)
    fun getResumeList(@RequestHeader headers: HttpHeaders): List<ResumeDTO>? {
        val user_session = headers[HeaderConst.SESSION_USER]?.getOrNull(0)
        if (user_session == null) throw AuthorityError()
        return BeanCovertor.convert(resumeService.getResumeList(user_session), object : TypeToken<List<ResumeDTO>>() {}.type)
    }

    @DeleteMapping(UrlConst.RESUME_DELETE)
    fun delete(@PathVariable(value = UrlConst.RESUME_PARAMS_RESUME_ID, required = true) resumeId: Long?): Boolean? {
        return resumeService.delete(resumeId)
    }

}
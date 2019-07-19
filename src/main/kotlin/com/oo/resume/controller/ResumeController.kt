package com.oo.resume.controller

import com.oo.resume.data.header.HeaderConst
import com.oo.resume.data.path.UrlConst
import com.oo.resume.data.response.ResumeDTO
import com.oo.resume.entity.Resume
import com.oo.resume.exception.AuthorityError
import com.oo.resume.exception.IlleageError
import com.oo.resume.service.interf.IAccountService
import com.oo.resume.service.interf.IResumeService
import com.oo.resume.util.BeanCovertor
import org.modelmapper.TypeToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

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

    @PostMapping(UrlConst.RESUME_CREATE_OR_UPDATE)
    fun createOrUpdate(@RequestBody pResume: ResumeDTO?, @RequestHeader headers: HttpHeaders): ResumeDTO? {
        val user_session = headers[HeaderConst.SESSION_USER]?.getOrNull(0)
        if (user_session == null) throw AuthorityError()
        if (pResume == null) throw IlleageError("请求参数为空")
        if (pResume.shortLink.isNullOrBlank()) throw IlleageError("短连接不合法")
        val resumeEntity = resumeService.getResumeByShortLink(pResume.shortLink)
        if (pResume.id != null) {
            if (resumeEntity == null) throw IlleageError("简历不存在")
            resumeSecurityValid(user_session, resumeEntity.account?.session_user)
        }
        shortLinkExistValid(pResume.shortLink, resumeEntity?.shortLink, pResume.id == null)
        val saveEntity = Resume()
        BeanCovertor.copyProperties(pResume, saveEntity, false)
        return BeanCovertor.convert(resumeService.createOrUpdate(saveEntity), ResumeDTO::class.java)
    }

    @GetMapping(UrlConst.RESUME_DETAIL)
    fun getResumeDetail(@PathVariable(value = UrlConst.RESUME_PARAMS_RESUME_ID, required = true) resumeId: Long?, @RequestHeader headers: HttpHeaders): List<ResumeDTO>? {
        val user_session = headers[HeaderConst.SESSION_USER]?.getOrNull(0)
        if (user_session == null) throw AuthorityError()
        if (resumeId == null) throw IlleageError("参数不合法")
        val resumeEntity = resumeService.getResumeByID(resumeId)
        if (resumeEntity == null) throw IlleageError("简历不存在")
        resumeSecurityValid(user_session, resumeEntity.account?.session_user)
        return BeanCovertor.convert(resumeEntity, ResumeDTO::class.java)
    }

    private fun shortLinkExistValid(targetShortLink: String?, sourceShortLink: String?, isNew: Boolean) {
        if (sourceShortLink == null) return
        if (isNew || targetShortLink != sourceShortLink) throw IlleageError("短连接已存在")
    }


    private fun resumeSecurityValid(request_user_session: String?, real_user_session: String?) {
        if (real_user_session != request_user_session) throw IlleageError("无权限修改此简历")
    }

    @DeleteMapping(UrlConst.RESUME_DELETE)
    fun delete(@PathVariable(value = UrlConst.RESUME_PARAMS_RESUME_ID, required = true) resumeId: Long?): Boolean? {
        return resumeService.delete(resumeId)
    }

}
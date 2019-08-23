package com.oo.resume.controller

import com.oo.resume.context.ContextPreference
import com.oo.resume.data.path.ResumeUrl
import com.oo.resume.data.response.ResumeDTO
import com.oo.resume.entity.Resume
import com.oo.resume.exception.IlleageError
import com.oo.resume.service.interf.IAccountService
import com.oo.resume.service.interf.IResumeService
import com.oo.resume.util.BeanCovertor
import org.modelmapper.TypeToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.web.bind.annotation.*

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-29 10:56
 *
 */
@RestController
@RequestMapping(ResumeUrl.PREFIX)
class ResumeController {

    @Autowired
    @Lazy
    lateinit var resumeService: IResumeService

    @Autowired
    @Lazy
    lateinit var accountService: IAccountService


    @GetMapping(ResumeUrl.PATH_LIST)
    fun getResumeList(): List<ResumeDTO>? {
        return BeanCovertor.convert(resumeService.getResumeList(ContextPreference.getAccount().id), object : TypeToken<List<ResumeDTO>>() {}.type)
    }

    @PostMapping(ResumeUrl.PATH_CREATE_OR_UPDATE)
    fun createOrUpdate(@RequestBody pResume: ResumeDTO?): ResumeDTO? {
        if (pResume == null) throw IlleageError("请求参数为空")
        if (pResume.shortLink.isNullOrBlank()) throw IlleageError("短连接不合法")
        val shortLinkEntity = resumeService.getResumeByShortLink(pResume.shortLink)
        if (pResume.id != null) {
            val resumeEntity = resumeService.getResumeByID(pResume.id, ContextPreference.getAccount().id)
            if (resumeEntity == null) throw IlleageError("简历不存在")
        }
        shortLinkExistValid(pResume.shortLink, shortLinkEntity?.shortLink, pResume.id == null)
        val saveEntity = Resume()
        BeanCovertor.copyProperties(pResume, saveEntity, false)
        return BeanCovertor.convert(resumeService.createOrUpdate(saveEntity), ResumeDTO::class.java)
    }

    @GetMapping(ResumeUrl.PATH_DETAIL)
    fun getResumeDetail(@PathVariable(value = ResumeUrl.PARAMS_RESUME_ID, required = true) resumeId: Long?): ResumeDTO? {
        if (resumeId == null) throw IlleageError("参数不合法")
        val resumeEntity = resumeService.getResumeByID(resumeId, ContextPreference.getAccount().id)
        if (resumeEntity == null) throw IlleageError("简历不存在")
        return BeanCovertor.convert(resumeEntity, ResumeDTO::class.java)
    }

    private fun shortLinkExistValid(targetShortLink: String?, sourceShortLink: String?, isNew: Boolean) {
        if (sourceShortLink == null) return
        if (isNew || targetShortLink != sourceShortLink) throw IlleageError("短连接已存在")
    }

    @DeleteMapping(ResumeUrl.PATH_DELETE)
    fun delete(@PathVariable(value = ResumeUrl.PARAMS_RESUME_ID, required = true) resumeId: Long?): Boolean? {
        return resumeService.delete(resumeId, ContextPreference.getAccount().id)
    }

}
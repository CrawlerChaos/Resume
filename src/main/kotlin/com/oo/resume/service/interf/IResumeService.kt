package com.oo.resume.service.interf

import com.oo.resume.entity.Resume

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-28 18:06
 *
 */
interface IResumeService {
    fun getResumeByShortLink(shortLink: String?): Resume?
    fun getResumeByID(id: Long?, accountID: Long?): Resume?
    fun getResumeList(accountID: Long?): List<Resume>?
    fun createOrUpdate(resume: Resume?): Resume?
    fun delete(resumeId: Long?, accountID: Long?): Boolean?
}
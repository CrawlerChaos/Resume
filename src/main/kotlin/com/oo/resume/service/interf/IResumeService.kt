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
    fun getResumeByUserId(id: String?): Resume?
    fun save(resume: Resume?): Resume?
    fun delete(resumeId: Long?): Boolean?
}
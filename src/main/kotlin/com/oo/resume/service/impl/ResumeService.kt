package com.oo.resume.service.impl

import com.oo.resume.entity.*
import com.oo.resume.repository.*
import com.oo.resume.service.interf.IResumeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-28 18:04
 *
 */
@Service
open class ResumeService : IResumeService {

    @Autowired
    @Lazy
    lateinit var resumeRepo: ResumeRepository
    @Autowired
    @Lazy
    lateinit var schoolRepo: SchoolRepository
    @Autowired
    @Lazy
    lateinit var labelRepo: LabelRepository
    @Autowired
    @Lazy
    lateinit var companyRepo: CompanyRepository
    @Autowired
    @Lazy
    lateinit var accountRepo: AccountRepository

    override fun getResumeList(accountID: Long?): List<Resume>? {
        if (accountID == null) return null
        return resumeRepo.findAllByAccountID(accountID)
    }

    override fun getResumeByShortLink(shortLink: String?): Resume? {
        if (shortLink == null) return null
        return resumeRepo.findByShortLink(shortLink)
    }

    override fun getResumeByID(id: Long?, accountID: Long?): Resume? {
        if (id == null || accountID == null) return null
        return resumeRepo.findResumeByID(id, accountID)
    }

    @Transactional
    override fun createOrUpdate(resume: Resume?): Resume? {
        if (resume == null) return null
        saveCompany(resume.company)
        dealEducation(resume.education)
        dealExperience(resume.experiences)
        return resumeRepo.save(resume)
    }

    override fun delete(resumeId: Long?, accountID: Long?): Boolean? {
        if (resumeId == null || accountID == null) return false
        val resume = resumeRepo.deleteResume(resumeId, accountID)
        return resume != null
    }


    private fun dealEducation(eductaions: List<Education>?) {
        eductaions?.forEach { education -> mergeSchool(education.school) }
    }

    private fun dealExperience(experiences: List<Experience>?) {
        experiences?.forEach { experience ->
            saveCompany(experience.company)
            experience.labels?.forEach { label -> mergeLabel(label) }
        }
    }


    private fun saveCompany(company: Company?) {
        if (company == null) return
        var companyEntity = companyRepo.findByName(company.name)
        if (companyEntity == null) {
            companyEntity = companyRepo.save(company)
        }
        company.id = companyEntity?.id ?: 0
    }

    private fun mergeSchool(school: School?) {
        if (school == null) return
        school.labels?.forEach { label -> mergeLabel(label) }
        var schoolEntity = schoolRepo.findByName(school.name)
        if (schoolEntity == null) {
            schoolEntity = schoolRepo.save(school)
        }
        school.id = schoolEntity?.id ?: 0
    }

    private fun mergeLabel(label: Label?) {
        if (label == null) return
        var labelEntity = labelRepo.findByName(label.name)
        if (labelEntity == null) {
            labelEntity = labelRepo.save(label)
        }
        label.id = labelEntity?.id ?: 0
    }
}
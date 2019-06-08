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
    lateinit var userRepo: UserRepository

    override fun getResumeByUserId(id: String?): Resume? {
        if (id == null) return null
        return resumeRepo.findResumeByUserID(id)
    }

    override fun getResumeByShortLink(shortLink: String?): Resume? {
        if (shortLink == null) return null
        return resumeRepo.findResumeByShortLink(shortLink);
    }

    @Transactional
    override fun save(resume: Resume?): Resume? {
        if (resume == null || illLeagal(resume)) return null
        saveUser(resume.user)
        saveCompany(resume.company)
        dealEducation(resume.education)
        dealExperience(resume.experiences)
        return resumeRepo.save(resume)
    }

    override fun delete(resumeId: Long?): Boolean? {
        if (resumeId == null) return false
        resumeRepo.deleteById(resumeId)
        return true
    }

    private fun illLeagal(resume: Resume): Boolean {
        if (resumeRepo.findResumeByShortLink(resume.shortLink) != null) return true
        return false
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

    private fun saveUser(user: User?) {
        if (user == null) return
        var userEntity: User? = null
        if (user.id != 0L) userEntity = userRepo.getOne(user.id)
        if (userEntity == null) {
            userEntity = userRepo.save(user)
            user.session_user = userEntity.session_user
        } else {
            user.session_user = userEntity.session_user
        }
    }

    private fun saveCompany(company: Company?) {
        if (company == null) return
        var companyEntity = companyRepo.findCompanyByName(company.name)
        if (companyEntity == null) {
            companyEntity = companyRepo.save(company)
        }
        company.id = companyEntity?.id ?: 0
    }

    private fun mergeSchool(school: School?) {
        if (school == null) return
        school.labels?.forEach { label -> mergeLabel(label) }
        var schoolEntity = schoolRepo.findSchoolByName(school.name)
        if (schoolEntity == null) {
            schoolEntity = schoolRepo.save(school)
        }
        school.id = schoolEntity?.id ?: 0
    }

    private fun mergeLabel(label: Label?) {
        if (label == null) return
        var labelEntity = labelRepo.findLabelByName(label.name)
        if (labelEntity == null) {
            labelEntity = labelRepo.save(label)
        }
        label.id = labelEntity?.id ?: 0
    }
}
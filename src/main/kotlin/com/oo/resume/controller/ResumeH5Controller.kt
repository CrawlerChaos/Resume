package com.oo.resume.controller

import com.oo.resume.exception.ApiError
import com.oo.resume.service.interf.IResumeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

/**
 * yangchao
 * cd.uestc.superyoung@gmail.com
 * 2019-05-16 10:00
 */

@Controller
@RequestMapping("/review")
class ResumeH5Controller {

    @Autowired
    @Lazy
    lateinit var resumeService: IResumeService

    @GetMapping("/{shortLink}")
    fun resume(@PathVariable(value = "shortLink", required = true) shortLink: String, model: Model): String {
        val resume = resumeService.getResumeByShortLink(shortLink)
        if (resume == null) throw Exception()
        else {
            model.addAttribute("resume", resume)
            return "resume"
        }
    }

}

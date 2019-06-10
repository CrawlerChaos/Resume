package com.oo.resume.controller

import com.oo.resume.constance.UrlConst
import com.oo.resume.entity.*
import com.oo.resume.service.interf.IResumeService
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
@RequestMapping(UrlConst.RESUME_PREFIX)
class ResumeController {

    @Autowired
    @Lazy
    lateinit var resumeService: IResumeService

    @GetMapping(UrlConst.RESUME_INFO)
    fun getResume(@PathVariable(value = UrlConst.RESUME_PARAMS_USER_ID, required = true) userId: String?): Resume? {
        return resumeService.getResumeByUserId(userId)
    }

    @DeleteMapping(UrlConst.RESUME_DELETE)
    fun delete(@PathVariable(value = UrlConst.RESUME_PARAMS_RESUME_ID, required = true) resumeId: Long?): Boolean? {
        return resumeService.delete(resumeId)
    }


//    @GetMapping("/init")
//    fun init(): Resume? {
//        resumeService.save(getYangChong())
//        return resumeService.save(getYangChao())
//    }
//
//    private fun getYangChong(): Resume? {
//        val synopsis = "· 勤奋好学\n" +
//                "· 喜欢美女\n" +
//                "· 热爱打炮"
//        val baseInfo = Account("18482101719", "123456", "杨冲", 23, 0, "786372615@qq.com",
//                "http://www.lgstatic.com/i/image2/M01/2F/09/CgoB5lzZKZOAZFlbAABCFbmSTGs258.jpg")
//
//        val edu = ArrayList<Education>()
//        edu.add(Education(School("电子科技大学成都学院", "http://www.lgstatic.com/images/schoolBadge/1014f6789b8e41bc8c904a3c4d0a1854.jpeg",
//                arrayListOf(Label("野鸡学校"))), "本科", "2013.9", "2017.6", "软件工程"))
//
//        val language = ArrayList<Language>()
//        language.add(Language("英语", 0.5f, "CET4"))
//
//        val technique = ArrayList<Technique>()
//        technique.add(Technique("Android", 0.5f))
//        technique.add(Technique("Java", 0.5f))
//
//        val professionalExperiences = ArrayList<Experience>()
//        professionalExperiences.add(Experience(Company("野鸡公司"),
//                "色情行业app", "帮老板撸管子",
//                "2018.11", null, "Android工程师"))
//        professionalExperiences.add(Experience(Company("创业"),
//                "创业公司当老板", "调戏女秘书",
//                "2017.09", "2018.11", "老板"))
//
//
//        return Resume("yangchong", baseInfo, synopsis, 2, Company("垃圾公司"), "Android", language, technique, professionalExperiences, edu)
//    }
//
//    private fun getYangChao(): Resume? {
//        val synopsis = "· 擅长Android性能优化，Overdraw，FPS，OOM等问题\n" +
//                "· 参与日活百万级App\"咕咚\"研发，并承担核心运动功能\n" +
//                "· 熟悉远程工作流程，在硅谷创新公司BorderXlab工作\n" +
//                "· 擅长Java Hook，Apt，Gradle Task挂接\n" +
//                "· 擅长MVVM架构"
//        val baseInfo = Account("13550310197", "161300", "杨超", 29, 0, "382987055@qq.com",
//                "http://www.lgstatic.com/i/image2/M01/2F/09/CgoB5lzZKZOAZFlbAABCFbmSTGs258.jpg")
//
//        val edu = ArrayList<Education>()
//        edu.add(Education(School("电子科技大学", "http://www.lgstatic.com/images/schoolBadge/1014f6789b8e41bc8c904a3c4d0a1854.jpeg",
//                arrayListOf(Label("985·院校"), Label("211·院校"))), "本科", "2008.9", "2012.6", "软件工程"))
//
//
//        val language = ArrayList<Language>()
//        language.add(Language("英语", 0.5f, "CET4"))
//        language.add(Language("日语", 0.5f, "日常交流"))
//
//        val technique = ArrayList<Technique>()
//        technique.add(Technique("Android", 0.9f))
//        technique.add(Technique("Java", 0.9f))
//        technique.add(Technique("Kotlin", 0.8f))
//        technique.add(Technique("Shell", 0.6f))
//        technique.add(Technique("Spring Boot", 0.5f))
//        technique.add(Technique("Html5", 0.5f))
//        technique.add(Technique("Thymeleaf", 0.5f))
//
//        val professionalExperiences = ArrayList<Experience>()
//        professionalExperiences.add(Experience(Company("美团点评", "http://www.lgstatic.com/i/image/M00/6A/05/Cgp3O1gW8zSAUwUsAABMptH-XY087.jpeg"),
//                "Saas收银产品研发", "支付，组件化设计，框架设计, 可视化小票插件",
//                "2017.09", null, "高级Android工程师"))
//
//        professionalExperiences.add(Experience(Company("BorderXLab", "http://www.lgstatic.com/i/image3/M00/01/BF/Cgq2xlpcVmCAAxJeAAG-D_F4x8g562.png", "https://bieyangapp.com/cn/"),
//                "对接纽约第五大道所有中高端时尚品牌及美国五大百货公司的海外购物平台，轻奢应用", "全模块开发，AAC+PB+ByRouter+Kotlin开发组件化设计，OCTO动态配置，灵活高效",
//                "2015.11", "2017.09", "Android工程师",
//                null, "https://yourls.bybieyang.com/oh5e"))
//
//        professionalExperiences.add(Experience(Company("咕咚", "http://www.lgstatic.com/i/image/M00/6B/7D/CgpFT1myAg-AZdj5AAAtZTOf1nw336.png", "https://www.codoon.com/"),
//                "咕咚是全球最大的运动社交平台，为6000万运动爱好者提供专业运动跟踪记录、运动知识、跑步健身训练、马拉松及骑行赛事、运动装备优选等全套解决方案", "运动核心模块研发，app性能优化，滤波&三角纠偏算法",
//                "2014.11", "2015.11", "Android工程师",
//                null, "https://static.codoon.com/app/android/codoonsports.apk?pm_vt=155835461956080&pm_r=cpm_https%3A%2F%2Fwww.codoon.com%2F_url"))
//
//        professionalExperiences.add(Experience(Company("MBPジャパン株式会社", "/images/logo_mbp.jpg", "http://www.mbpsoft.co.jp/"),
//                "面向中国国内和日本客户提供系统应用开发，基于大型ERP系统的二次开发等离岸外包服务及BPO服务", "东芝プロジェクト(帐票开発)负责<试算表>＜损益表＞等开发，以及测试式样书撰写，单体测试以及结合测试。",
//                "2012.06", "2014.03", "Java开发工程师",
//                arrayListOf()))//"SVF", "JDE", "Spring MVC"
//
//        return Resume("yangchao", baseInfo, synopsis, 7, Company("美团点评"), "Android", language, technique, professionalExperiences, edu)
//    }
}
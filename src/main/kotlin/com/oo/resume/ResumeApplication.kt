package com.oo.resume

import org.springframework.boot.SpringApplication
import org.springframework.boot.actuate.autoconfigure.trace.http.HttpTraceAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

//Jpa CreateTime UpdateTime
@EnableJpaAuditing
@SpringBootApplication(exclude = [HttpTraceAutoConfiguration::class])
@ServletComponentScan
open class ResumeApplication : SpringBootServletInitializer() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ResumeApplication::class.java, *args)
        }
    }

}

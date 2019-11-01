package com.oo.resume.config

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-10-31 11:15
 *
 */
@EnableWebSecurity
open class SecurityConfig : WebSecurityConfigurerAdapter(){
    override fun configure(http: HttpSecurity?) {
        if (http == null) return
        http.authorizeRequests()
                .antMatchers("/actuator/**").hasRole("SUPER")
                .anyRequest().permitAll()
                .and().formLogin()
                .and().httpBasic()
                .and().csrf().disable()

    }
}
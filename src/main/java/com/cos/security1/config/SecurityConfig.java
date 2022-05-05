package com.cos.security1.config;

import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // spring security 필터(현재 config class)가 스프링 필터체인에 등록됨.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // @Secured({ROLE_NAME}) 활성화, @PreAuthorize @PostAuthorize 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 인증만 되면 접근 가능
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // login 주소가 호출되면 시큐리티가 대신 로그인을 진행해줌.
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm");
    }
}

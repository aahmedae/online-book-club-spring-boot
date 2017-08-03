package com.github.aahmedae.onlinebookclub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.aahmedae.onlinebookclub.security.*;

/**
 * File: WebSecurityConfig.java
 * Description: Security config for the system
 * Author: Asad Ahmed
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        // frameoptions for headers disables so that h2 console can be accessed
        // public endpoints are open to all
        // while all other endpoints are assumed to be secure and require JWT authentication

        http.csrf().disable().authorizeRequests().and()
                .headers().frameOptions().disable().and().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console").permitAll()
                .antMatchers("/h2-console/*").permitAll()
                .antMatchers("/api/v1/book/public/*").permitAll()
                .antMatchers("/api/v1/user/public/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        // default account for development
        auth.inMemoryAuthentication().withUser("admin").password("12345").roles("ADMIN");
    }
}

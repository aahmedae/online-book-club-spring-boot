package com.github.aahmedae.onlinebookclub.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.github.aahmedae.onlinebookclub.service.TokenAuthenticationService;


/**
 * File: JWTAuthenticationFilter.java
 * Description: Generic authentication filter
 * Author: Asad Ahmed
 */
public class JWTAuthenticationFilter extends GenericFilterBean
{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException
    {
        Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest)request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
}

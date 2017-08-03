package com.github.aahmedae.onlinebookclub.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.Date;

/**
 * File: TokenAuthenticationService.java
 * Description: Service for filtering authentication requests
 * Author: Asad Ahmed
 */
public class TokenAuthenticationService
{
    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "BDE175C6-089E-4EBD-9ADF-97944F0F2A69";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    // region Public methods

    // Add authentication information to the response header
    public static void addAuthentication(HttpServletResponse res, String username)
    {
        String JWT = getUserTokenString(username);
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    // Parse the JWT header information and authenticate the request
    public static Authentication getAuthentication(HttpServletRequest request)
    {
        String token = request.getHeader(HEADER_STRING);
        //String realToken = request.getParameter("username");

        if (token != null)
        {
            // parse token
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, " "))
                    .getBody()
                    .getSubject();

            return (user != null ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) : null);
        }

        return null;
    }

    // endregion

    // region Static validation functions

    // Get the user token for the given username
    public static String getUserTokenString(String username)
    {
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return token;
    }

    // Parse the username from the token
    public static String parseUsername(String token)
    {
        String username =  Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, " "))
                .getBody()
                .getSubject();
        return username;
    }

    // Determine if the token is valid for the given username
    public static boolean validateUserToken(String token, String username)
    {
        String user = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return (user.equals(username));
    }

    // endregion
}

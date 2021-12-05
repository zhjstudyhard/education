package com.education.controller.login;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.SignatureException;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-12-05-22-41
 */
@RestController
public class JwtExceptionController {
    /**
     * 重新抛出异常
     */
    @RequestMapping("/authenticationException")
    public void authenticationException(HttpServletRequest request) throws AuthenticationException,RuntimeException {
        AuthenticationException authenticationException = (AuthenticationException) request.getAttribute("authenticationException");
        throw authenticationException;
    }

    @RequestMapping("/signatureException")
    public void signatureException(HttpServletRequest request) throws SignatureException {
        throw ((SignatureException) request.getAttribute("signatureException"));
    }

}

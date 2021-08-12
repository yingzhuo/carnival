/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.core;

import com.github.yingzhuo.carnival.captcha.Captcha;
import com.github.yingzhuo.carnival.captcha.CaptchaDao;
import com.github.yingzhuo.carnival.captcha.CaptchaHandler;
import com.github.yingzhuo.carnival.captcha.CaptchaService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 应卓
 * @since 1.10.6
 */
public class CaptchaFilter extends OncePerRequestFilter {

    private CaptchaService captchaService;
    private CaptchaDao captchaDao;
    private CaptchaHandler captchaHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final Captcha captcha = captchaService.getCaptcha();
        this.captchaDao.save(captcha.getAccessKey(), captcha.getCaptcha());
        this.captchaHandler.handle(captcha, request, response);
    }

    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    public void setCaptchaDao(CaptchaDao captchaDao) {
        this.captchaDao = captchaDao;
    }

    public void setCaptchaHandler(CaptchaHandler captchaHandler) {
        this.captchaHandler = captchaHandler;
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.patchca;

import org.patchca.service.CaptchaService;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

public class PatchcaFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatchcaFilter.class);

    private String patchcaSessionAttributeName = "PATCHCA_SESSION_ATTRIBUTE_NAME";
    private CaptchaService captchaService = new ConfigurableCaptchaService();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setContentType("image/png");
        response.setHeader("cache", "no-cache");

        HttpSession session = request.getSession(true);
        OutputStream outputStream = response.getOutputStream();
        String patchca = EncoderHelper.getChallangeAndWriteImage(captchaService, "png", outputStream);
        session.setAttribute(patchcaSessionAttributeName, patchca);
        LOGGER.info("SesstionAttribute: {}={}", patchcaSessionAttributeName, patchca);

        outputStream.flush();
        outputStream.close();
    }

    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    public void setPatchcaSessionAttributeName(String patchcaSessionAttributeName) {
        this.patchcaSessionAttributeName = patchcaSessionAttributeName;
    }
}

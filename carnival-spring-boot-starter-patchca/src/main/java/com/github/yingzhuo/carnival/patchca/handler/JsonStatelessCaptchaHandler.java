/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.patchca.handler;

import com.github.yingzhuo.carnival.json.Json;
import com.github.yingzhuo.carnival.spring.JacksonUtils;
import org.patchca.service.EncodedCaptcha;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 应卓
 * @since 1.6.2
 */
public class JsonStatelessCaptchaHandler extends AbstractStatelessCaptchaHandler {

    private final boolean outputCaptchaValue;

    public JsonStatelessCaptchaHandler() {
        this(false);
    }

    public JsonStatelessCaptchaHandler(boolean outputCaptchaValue) {
        this.outputCaptchaValue = outputCaptchaValue;
    }

    @Override
    protected void doHandle(EncodedCaptcha captcha, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        final Json json = Json.newInstance()
                .code(HttpStatus.OK)
                .payload("accessKey", captcha.getAccessKey())
                .payload("encodedImage", captcha.getEncodeImage());

        if (outputCaptchaValue) {
            json.payload("captcha", captcha.getCaptcha());
        }

        final String body = JacksonUtils.writeValueAsString(json);

        final ServletOutputStream os = response.getOutputStream();
        os.print(body);
        os.flush();
        os.close();
    }

}

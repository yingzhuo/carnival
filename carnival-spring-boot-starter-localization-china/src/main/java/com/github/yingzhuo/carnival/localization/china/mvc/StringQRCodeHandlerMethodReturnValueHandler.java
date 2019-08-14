/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.localization.china.mvc;

import com.github.yingzhuo.carnival.localization.china.tool.QRCodeCreator;
import com.github.yingzhuo.carnival.localization.china.tool.StringQRCode;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 应卓
 */
public class StringQRCodeHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final QRCodeCreator creator;

    public StringQRCodeHandlerMethodReturnValueHandler(QRCodeCreator creator) {
        this.creator = creator;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return StringQRCode.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) {
        mavContainer.setView(new StringQRCodeView(creator, (StringQRCode) returnValue));
    }

    public static class StringQRCodeView implements View {

        private final QRCodeCreator creator;
        private final StringQRCode qrCode;

        public StringQRCodeView(QRCodeCreator creator, StringQRCode qrCode) {
            this.creator = creator;
            this.qrCode = qrCode;
        }

        @Override
        public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.setContentType("image/png");
            creator.create(response.getOutputStream(), qrCode.getContent(), qrCode.getSize(), qrCode.getFormat(), qrCode.getErrorCorrectionLevel());
        }
    }

}
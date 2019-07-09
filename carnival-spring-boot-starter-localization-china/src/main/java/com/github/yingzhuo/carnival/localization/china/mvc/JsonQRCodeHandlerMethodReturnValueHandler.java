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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yingzhuo.carnival.localization.china.tool.JsonQRCode;
import com.github.yingzhuo.carnival.localization.china.tool.QRCodeCreator;
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
public class JsonQRCodeHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final QRCodeCreator creator;
    private final ObjectMapper objectMapper;

    public JsonQRCodeHandlerMethodReturnValueHandler(QRCodeCreator creator, ObjectMapper objectMapper) {
        this.creator = creator;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return JsonQRCode.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) {
        mavContainer.setView(new JsonQRCodeView((JsonQRCode) returnValue, creator, objectMapper));
    }

    private static class JsonQRCodeView implements View {

        private final JsonQRCode qrCode;
        private final QRCodeCreator creator;
        private final ObjectMapper objectMapper;

        public JsonQRCodeView(JsonQRCode qrCode, QRCodeCreator creator, ObjectMapper objectMapper) {
            this.qrCode = qrCode;
            this.creator = creator;
            this.objectMapper = objectMapper;
        }

        @Override
        public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.setContentType("image/png");
            final String content = objectMapper.writeValueAsString(model);
            creator.create(response.getOutputStream(), content, qrCode.getSize(), qrCode.getFormat(), qrCode.getErrorCorrectionLevel());
        }
    }

}

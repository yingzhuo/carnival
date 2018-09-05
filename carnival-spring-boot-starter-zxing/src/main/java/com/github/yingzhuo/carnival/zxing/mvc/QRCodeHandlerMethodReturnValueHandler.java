/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.zxing.mvc;

import com.github.yingzhuo.carnival.zxing.QRCode;
import com.github.yingzhuo.carnival.zxing.QRCodeCreator;
import com.github.yingzhuo.carnival.zxing.autoconfig.QRCodeWebAutoConfig;
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
public class QRCodeHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final QRCodeCreator creator;
    private final QRCodeWebAutoConfig.Props props;

    public QRCodeHandlerMethodReturnValueHandler(QRCodeCreator creator, QRCodeWebAutoConfig.Props props) {
        this.creator = creator;
        this.props = props;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return QRCode.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        QRCode qrCode = (QRCode) returnValue;
        mavContainer.setView(new QRCodeView(qrCode.getContent(), creator, props));
    }

    private static class QRCodeView implements View {

        private final String content;
        private final QRCodeCreator creator;
        private final QRCodeWebAutoConfig.Props props;

        public QRCodeView(String content, QRCodeCreator creator, QRCodeWebAutoConfig.Props props) {
            this.content = content;
            this.creator = creator;
            this.props = props;
        }

        @Override
        public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.setContentType("image/png");
            creator.create(response.getOutputStream(), content, props.getSize(), props.getImageFormat(), props.getErrorCorrectionLevel());
        }
    }

}

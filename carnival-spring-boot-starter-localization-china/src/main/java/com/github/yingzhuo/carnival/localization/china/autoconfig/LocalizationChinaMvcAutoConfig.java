/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.localization.china.autoconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yingzhuo.carnival.localization.china.mvc.JsonQRCodeHandlerMethodReturnValueHandler;
import com.github.yingzhuo.carnival.localization.china.mvc.StringQRCodeHandlerMethodReturnValueHandler;
import com.github.yingzhuo.carnival.localization.china.tool.QRCodeCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
public class LocalizationChinaMvcAutoConfig implements WebMvcConfigurer {

    @Autowired
    private QRCodeCreator creator;

    @Autowired(required = false)
    private ObjectMapper om;

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(new StringQRCodeHandlerMethodReturnValueHandler(creator));
        handlers.add(new JsonQRCodeHandlerMethodReturnValueHandler(creator, om != null ? om : new ObjectMapper()));
    }
}

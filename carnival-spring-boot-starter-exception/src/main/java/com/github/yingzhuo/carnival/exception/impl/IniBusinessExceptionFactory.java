/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception.impl;

import com.github.yingzhuo.carnival.exception.BusinessException;
import com.github.yingzhuo.carnival.exception.BusinessExceptionFactory;
import lombok.extern.slf4j.Slf4j;
import org.ini4j.Ini;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 应卓
 */
@Slf4j
public class IniBusinessExceptionFactory implements BusinessExceptionFactory, InitializingBean {

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();
    private final String location;
    private Ini ini = null;

    public IniBusinessExceptionFactory(String iniLocation) {
        this.location = iniLocation;
    }

    @Override
    public BusinessException create(String code) {

        if (!StringUtils.hasText(code)) {
            throw new IllegalArgumentException("'" + code + "' is NOT a valid code");
        }

        int firstDot = code.indexOf(".");
        if (firstDot == -1) {
            throw new IllegalArgumentException("'" + code + "' is NOT a valid code");
        }

        String section = code.substring(0, firstDot);
        String option = code.substring(firstDot + 1, code.length());

        final String message = ini.get(section, option, String.class);

        if (!StringUtils.hasText(message)) {
            throw new IllegalArgumentException("'" + code + "' is NOT a valid code");
        }

        return new BusinessException(code, message);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("ini-location: {}", location);
        final InputStream inputStream = resourceLoader.getResource(location).getInputStream();
        this.ini = new Ini(inputStream);

        try {
            inputStream.close();
        } catch (IOException e) {
            // NOP
        }
    }

}

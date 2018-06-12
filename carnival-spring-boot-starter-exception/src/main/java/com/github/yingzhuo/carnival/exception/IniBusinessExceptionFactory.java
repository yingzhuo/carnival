/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception;

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
public class IniBusinessExceptionFactory implements BusinessExceptionFactory, InitializingBean {

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();
    private Ini ini = null;
    private String location;

    public IniBusinessExceptionFactory() {
    }

    public IniBusinessExceptionFactory(String location) {
        this.location = location;
    }

    @Override
    public BusinessException create(String code) {

        if (!StringUtils.hasText(code)) {
            throw new IllegalArgumentException("'" + code + "' is NOT a valid code");
        }

        String[] pair = code.split("\\.");

        if (pair.length != 2) {
            throw new IllegalArgumentException("'" + code + "' is NOT a valid code");
        }

        final String message = ini.get(pair[0], pair[1], String.class);

        if (!StringUtils.hasText(message)) {
            throw new IllegalArgumentException("'" + code + "' is NOT a valid code");
        }

        return new BusinessException(code, message);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final InputStream inputStream = resourceLoader.getResource(location).getInputStream();
        this.ini = new Ini(inputStream);

        try {
            inputStream.close();
        } catch (IOException e) {
            // NOP
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

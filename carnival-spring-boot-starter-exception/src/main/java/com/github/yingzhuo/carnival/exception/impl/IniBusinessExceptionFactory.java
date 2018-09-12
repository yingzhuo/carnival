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

import com.github.yingzhuo.carnival.common.io.ini.Ini;
import com.github.yingzhuo.carnival.exception.BusinessException;
import com.github.yingzhuo.carnival.exception.BusinessExceptionFactory;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

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

        final String message = ini.get(code);

        if (!StringUtils.hasText(message)) {
            throw new IllegalArgumentException("'" + code + "' is NOT a valid code");
        }

        return new BusinessException(code, message);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("ini-location: {}", location);
        val resource = resourceLoader.getResource(location);
        this.ini = new Ini(resource);
    }

    public Ini getIni() {
        return ini;
    }

}

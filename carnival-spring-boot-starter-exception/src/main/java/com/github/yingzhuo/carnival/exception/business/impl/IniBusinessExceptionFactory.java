/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception.business.impl;

import com.github.yingzhuo.carnival.exception.business.BusinessException;
import com.github.yingzhuo.carnival.exception.business.BusinessExceptionFactory;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.ini4j.Wini;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * @author 应卓
 */
@Slf4j
public class IniBusinessExceptionFactory implements BusinessExceptionFactory {

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();
    private final String location;
    private Wini ini;

    public IniBusinessExceptionFactory(String iniLocation) {
        this.location = iniLocation;
    }

    @Override
    public BusinessException create(String code, Object... params) {

        if (!StringUtils.hasText(code)) {
            throw new IllegalArgumentException("'" + code + "' is NOT a valid code");
        }

        final String[] sectionNameAndKey = code.split("\\.");

        if (sectionNameAndKey.length != 2) {
            throw new IllegalArgumentException("'" + code + "' is NOT a valid code");
        }

        var message = ini.get(sectionNameAndKey[0], sectionNameAndKey[1]);

        if (!StringUtils.hasText(message)) {
            throw new IllegalArgumentException("'" + code + "' is NOT a valid code");
        }

        for (val param : params) {
            message = message.replaceFirst("\\{}", param.toString());
        }

        message = message.replaceAll("\\{}", "");
        return new BusinessException(code, message);
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        log.debug("ini-location: {}", location);
        val resource = resourceLoader.getResource(location);
        this.ini = new Wini(resource.getInputStream());
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.log;

import org.springframework.boot.logging.LogLevel;
import org.springframework.core.convert.converter.Converter;

/**
 * @author 应卓
 * @since 1.10.10
 */
public class ConfigurableLoggerConverter implements Converter<String, ConfigurableLogger> {

    @Override
    public ConfigurableLogger convert(String source) {
        final String[] parts = source.split("\\|", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException();
        }

        return ConfigurableLogger
                .builder()
                .level(LogLevel.valueOf(parts[0].toUpperCase()))
                .name(parts[1])
                .build();
    }

}

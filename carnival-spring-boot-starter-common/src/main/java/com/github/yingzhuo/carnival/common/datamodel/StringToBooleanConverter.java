/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.datamodel;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author 应卓
 * @since 1.5.1
 */
@Component
@ConfigurationPropertiesBinding
public class StringToBooleanConverter implements Converter<String, Boolean> {

    @Override
    public Boolean convert(String source) {

        if ("true".equalsIgnoreCase(source) ||
                "1".equals(source) ||
                "yes".equalsIgnoreCase(source) ||
                "on".equalsIgnoreCase(source) ||
                "y".equalsIgnoreCase(source)) {
            return Boolean.TRUE;
        }

        if ("false".equalsIgnoreCase(source) ||
                "0".equals(source) ||
                "no".equalsIgnoreCase(source) ||
                "off".equalsIgnoreCase(source) ||
                "n".equalsIgnoreCase(source)) {
            return Boolean.FALSE;
        }

        throw new IllegalArgumentException("Invalid string: '" + source + "'");
    }

}

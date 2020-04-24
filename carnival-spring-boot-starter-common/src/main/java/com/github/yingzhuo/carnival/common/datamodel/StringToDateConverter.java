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

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

/**
 * @author 应卓
 * @since 1.5.1
 */
@Component
@ConfigurationPropertiesBinding
public class StringToDateConverter implements Converter<String, Date> {

    private final static String[] PATTERNS = {
            "yyyy-MM-dd",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSS Z",
            "yyyyMMdd",
            "yyMMddHHmmss",
            "yyMMddHHmmssSSS",
            "dd/MM/yy",
            "dd/MM/yyyy",
            "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss",
            "yyyy/MM/dd HH:mm:ss.SSS",
    };

    @Override
    public Date convert(String source) {
        try {
            return DateUtils.parseDateStrictly(source, PATTERNS);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

}

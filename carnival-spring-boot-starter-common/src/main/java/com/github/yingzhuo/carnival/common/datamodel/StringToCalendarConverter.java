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

import java.util.Calendar;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.5.1
 */
public class StringToCalendarConverter implements Converter<String, Calendar> {

    private StringToDateConverter converter = new StringToDateConverter();

    @Override
    public Calendar convert(String source) {
        return DateUtils.toCalendar(Objects.requireNonNull(converter.convert(source)));
    }

}

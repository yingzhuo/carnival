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

import com.google.common.net.HostAndPort;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author 应卓
 * @since 1.3.2
 */
public class HostAndPortFormatter implements Formatter<HostAndPort>, Converter<String, HostAndPort> {

    @Override
    public HostAndPort parse(String text, Locale locale) throws ParseException {
        try {
            return HostAndPort.fromString(text);
        } catch (Exception e) {
            throw new ParseException("invalid formatter", 0);
        }
    }

    @Override
    public String print(HostAndPort object, Locale locale) {
        return object.toString();
    }

    @Override
    public HostAndPort convert(String hostPortString) {
        try {
            return parse(hostPortString, Locale.getDefault());
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}

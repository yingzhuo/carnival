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

import java.text.ParseException;
import java.util.Locale;

/**
 * @author 应卓
 * @since 1.3.2
 */
public class HostAndPortFormatter extends AbstractObjectFormatter<HostAndPort> {

    @Override
    public HostAndPort parse(String text, Locale locale) throws ParseException {
        try {
            return HostAndPort.fromString(text);
        } catch (Exception e) {
            throw new ParseException("invalid formatter", 0);
        }
    }

}

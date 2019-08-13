/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.datamodel.support;

import org.springframework.format.Formatter;

import java.util.Locale;

/**
 * @author 应卓
 */
public final class NopStringFormatter implements Formatter<String> {

    public static final Formatter<String> INSTANCE = new NopStringFormatter();

    private NopStringFormatter() {
    }

    @Override
    public String parse(String text, Locale locale) {
        return text;
    }

    @Override
    public String print(String object, Locale locale) {
        return object;
    }

}

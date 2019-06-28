/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.secret.support;

import org.springframework.format.Printer;

import java.util.Locale;

/**
 * @author 应卓
 */
public class StringPrinter implements Printer<String> {

    public static final Printer<String> INSTANCE = new StringPrinter();

    @Override
    public String print(String object, Locale locale) {
        return object;
    }

}

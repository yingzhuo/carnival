/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.autoconfig;

import com.github.yingzhuo.carnival.common.datamodel.DateTimeFormat;
import com.github.yingzhuo.carnival.common.datamodel.IntCurrencyFormat;
import com.github.yingzhuo.carnival.common.datamodel.LongCurrencyFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistry;

/**
 * @author 应卓
 * @since 1.6.6
 */
public class FormatterAutoConfig {

    @Autowired(required = false)
    public void config(FormatterRegistry registry) {
        if (registry != null) {
            registry.addFormatterForFieldAnnotation(new DateTimeFormat.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new IntCurrencyFormat.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new LongCurrencyFormat.FormatterFactory());
        }
    }

}

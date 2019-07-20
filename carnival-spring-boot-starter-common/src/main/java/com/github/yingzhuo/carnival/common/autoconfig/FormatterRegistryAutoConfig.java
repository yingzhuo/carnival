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

import com.github.yingzhuo.carnival.common.datamodel.BooleanFormat;
import com.github.yingzhuo.carnival.common.datamodel.DateTimeFormat;
import com.github.yingzhuo.carnival.common.datamodel.IntCurrencyFormat;
import com.github.yingzhuo.carnival.common.datamodel.LongCurrencyFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistry;

import java.util.Optional;

/**
 * @author 应卓
 */
public class FormatterRegistryAutoConfig {

    @Autowired(required = false)
    public void config(FormatterRegistry registry) {
        Optional.ofNullable(registry).ifPresent(x -> {
            x.addFormatter(new BooleanFormat());
            x.addFormatterForFieldAnnotation(new DateTimeFormat.FormatterFactory());
            x.addFormatterForFieldAnnotation(new IntCurrencyFormat.FormatterFactory());
            x.addFormatterForFieldAnnotation(new LongCurrencyFormat.FormatterFactory());
        });
    }

}

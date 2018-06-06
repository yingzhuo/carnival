/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datamodel.autoconfig;

import com.github.yingzhuo.carnival.datamodel.account.GenderFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.FormatterRegistry;

/**
 * @author 应卓
 */
public class FormatterRegistryAutoConfig {

    @Autowired
    public void config(FormatterRegistry registry) {
        registry.addFormatter(new GenderFormatter());
    }

}

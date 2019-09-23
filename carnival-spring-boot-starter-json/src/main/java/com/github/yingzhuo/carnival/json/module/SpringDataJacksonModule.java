/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.json.module;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.yingzhuo.carnival.json.module.jackson.PageMixIn;
import org.springframework.data.domain.Page;

/**
 * @author 应卓
 */
public class SpringDataJacksonModule extends SimpleModule {

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.setMixInAnnotations(Page.class, PageMixIn.class);
    }

}

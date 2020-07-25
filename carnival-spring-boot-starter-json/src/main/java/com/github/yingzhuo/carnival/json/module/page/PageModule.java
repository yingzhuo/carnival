/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.json.module.page;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.domain.Page;

/**
 * @author 应卓
 * @since 1.6.17
 */
public class PageModule extends Module {

    public PageModule() {
    }

    @Override
    public String getModuleName() {
        return "PageJacksonModule";
    }

    @Override
    public Version version() {
        return new Version(0, 1, 0, "", null, null);
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(Page.class, PageMixIn.class);
    }

    @JsonDeserialize(as = SimplePageImpl.class)
    private interface PageMixIn {
    }

}

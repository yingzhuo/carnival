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

import com.github.yingzhuo.carnival.common.io.ResourceOptional;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.util.Locale;

/**
 * @author 应卓
 * @since 1.4.1
 */
public class ResourceOptionalFormatter extends AbstractObjectFormatter<ResourceOptional> {

    private final String sep;

    public ResourceOptionalFormatter() {
        this(",");
    }

    public ResourceOptionalFormatter(String sep) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(sep));
        this.sep = sep;
    }

    @Override
    public ResourceOptional parse(String text, Locale locale) {
        return ResourceOptional.of(text.split(sep));
    }

}

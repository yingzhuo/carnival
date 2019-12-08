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

import com.github.yingzhuo.carnival.common.io.ResourceText;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * @author 应卓
 * @since 1.3.3
 */
public class ResourceTextFormatter extends AbstractObjectFormatter<ResourceText> {

    @Override
    public ResourceText parse(String text, Locale locale) {
        return new ResourceText.SimpleResourceText(text, StandardCharsets.UTF_8);
    }

}

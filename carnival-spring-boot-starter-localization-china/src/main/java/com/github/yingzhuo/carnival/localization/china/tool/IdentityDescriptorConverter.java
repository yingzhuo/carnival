/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.localization.china.tool;

import org.springframework.core.convert.converter.Converter;

/**
 * @author 应卓
 * @since 1.3.6
 */
public class IdentityDescriptorConverter implements Converter<String, IdentityDescriptor> {

    private final IdentityParser identityParser;

    public IdentityDescriptorConverter(IdentityParser identityParser) {
        this.identityParser = identityParser != null ? identityParser : new IdentityParserImpl();
    }

    @Override
    public IdentityDescriptor convert(String source) {
        return identityParser.parse(source).orElse(null);
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.io;

import org.springframework.core.convert.converter.Converter;

/**
 * @author 应卓
 * @since 1.10.22
 */
public class ResourceOptionConverter implements Converter<CharSequence, ResourceOption> {

    @Override
    public ResourceOption convert(CharSequence source) {
        return ResourceOption.fromCommaSeparatedString(source.toString());
    }

}

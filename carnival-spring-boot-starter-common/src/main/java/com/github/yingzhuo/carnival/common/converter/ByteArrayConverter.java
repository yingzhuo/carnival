/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.converter;

import com.github.yingzhuo.carnival.common.util.HexUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * @author 应卓
 * @since 1.9.10
 */
@SuppressWarnings("NullableProblems")
public class ByteArrayConverter implements Converter<CharSequence, byte[]> {

    @Override
    public byte[] convert(CharSequence source) {
        if (source == null) return null;
        return HexUtils.decode(source);
    }

}

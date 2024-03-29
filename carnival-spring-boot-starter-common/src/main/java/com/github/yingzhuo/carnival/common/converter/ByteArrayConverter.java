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
        try {
            return HexUtils.decode(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

}

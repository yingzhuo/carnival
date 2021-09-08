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

import org.springframework.core.convert.converter.Converter;

import java.awt.*;

/**
 * @author 应卓
 * @since 1.10.16
 */
public class ColorConverter implements Converter<CharSequence, Color> {

    @Override
    public Color convert(CharSequence source) {

        try {
            String s = source.toString();
            if (s.startsWith("#")) {
                s = s.substring(1);
            }

            int red = Integer.parseInt(s.substring(0, 2), 16);
            int green = Integer.parseInt(s.substring(2, 4), 16);
            int blue = Integer.parseInt(s.substring(4, 6), 16);
            return new Color(red, green, blue);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

}

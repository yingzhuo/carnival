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

import org.springframework.core.convert.converter.Converter;

import java.awt.*;

/**
 * @author 应卓
 * @since 1.6.3
 */
public class StringToColorConverter implements Converter<String, Color> {

    @Override
    public Color convert(String text) {

        if (text.length() != 6) {
            throw new IllegalArgumentException("Invalid color.");
        }

        final String r = text.substring(0, 2);
        final String g = text.substring(2, 4);
        final String b = text.substring(4, 6);

        try {
            return new Color(
                    Integer.parseInt(r, 16),
                    Integer.parseInt(g, 16),
                    Integer.parseInt(b, 16)
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid color.");
        }
    }

}

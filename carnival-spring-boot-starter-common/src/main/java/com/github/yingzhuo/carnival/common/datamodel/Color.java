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

import org.springframework.format.Formatter;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Locale;

/**
 * @author 应卓
 */
public class Color extends java.awt.Color implements Serializable {

    private static final long serialVersionUID = -310360044867264845L;

    public Color(int r, int g, int b) {
        super(r, g, b);
    }

    @Override
    public String toString() {
        return Integer.toHexString(getRed()) + Integer.toHexString(getGreen()) + Integer.toHexString(getBlue());
    }

    public static final class ColorFormatter implements Formatter<Color> {

        @Override
        public Color parse(String text, Locale locale) throws ParseException {

            if (text.length() != 6) {
                throw new ParseException("Cannot parse color from '" + text + "'", 0);
            }

            final String sr = text.substring(0, 2);
            final String sg = text.substring(2, 4);
            final String sb = text.substring(4, 6);

            try {
                return new Color(
                        Integer.valueOf(sr, 16),
                        Integer.valueOf(sg, 16),
                        Integer.valueOf(sb, 16)
                );
            } catch (NumberFormatException e) {
                throw new ParseException(e.getMessage(), 0);
            }
        }

        @Override
        public String print(Color color, Locale locale) {
            return color.toString();
        }
    }

}

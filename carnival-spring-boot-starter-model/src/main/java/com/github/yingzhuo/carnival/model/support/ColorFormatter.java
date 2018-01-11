/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.model.support;

import com.github.yingzhuo.carnival.model.Color;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author 应卓
 */
public class ColorFormatter implements Formatter<Color> {

    public final static Formatter<?> INSTANCE = new ColorFormatter();

    private ColorFormatter() {
    }

    @Override
    public Color parse(String text, Locale locale) throws ParseException {
        if (text.length() == 6) {
            text = text.toUpperCase();

            String r = text.substring(0, 2);
            String g = text.substring(2, 4);
            String b = text.substring(4, 6);

            try {
                return new Color(
                        Integer.parseInt(r, 16),
                        Integer.parseInt(g, 16),
                        Integer.parseInt(b, 16)
                );
            } catch (NumberFormatException e) {
                throw new ParseException(e.getMessage(), 0);
            }
        }

        throw new ParseException("For input: " + text, 0);
    }

    @Override
    public String print(Color color, Locale locale) {
        return (colorToHex(color.getR()) + colorToHex(color.getG()) + colorToHex(color.getB())).toUpperCase();
    }

    private String colorToHex(int c) {
        String col = Integer.toHexString(c);
        if (col.length() == 1) {
            return "0" + col;
        } else {
            return col;
        }
    }
}

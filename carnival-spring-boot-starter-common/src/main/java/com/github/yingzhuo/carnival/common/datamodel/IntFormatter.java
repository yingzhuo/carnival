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

import com.github.yingzhuo.carnival.common.util.AtoiUtils;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author 应卓
 * @since 1.3.2
 */
public class IntFormatter extends AbstractObjectFormatter<Integer> {

    @Override
    public Integer parse(String text, Locale locale) throws ParseException {
        try {
            return AtoiUtils.toInt(text);
        } catch (Exception e) {
            throw new ParseException("invalid format", 0);
        }
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.pinyin.impl;

import com.github.yingzhuo.carnival.pinyin.PinyinTool;
import com.github.yingzhuo.carnival.pinyin.PinyinUtils;

/**
 * @author 应卓
 */
public class SimplePinyinTool implements PinyinTool {

    @Override
    public String toFirstSpell(String chinese, boolean ignoreNonChineseChar, boolean lowerCase, String separator) {
        return PinyinUtils.cn2FirstSpell(chinese, ignoreNonChineseChar, lowerCase, separator);
    }

    @Override
    public String toSpell(String chinese, boolean ignoreNonChineseChar, boolean lowerCase, String separator) {
        return PinyinUtils.cn2Spell(chinese, ignoreNonChineseChar, lowerCase, separator);
    }

}

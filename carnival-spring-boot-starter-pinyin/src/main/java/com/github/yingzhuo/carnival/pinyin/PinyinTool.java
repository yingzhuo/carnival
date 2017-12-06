/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.pinyin;

public interface PinyinTool {

    public String toFirstSpell(String chinese, boolean ignoreNonChineseChar, boolean lowerCase, String separator);

    default public String toFirstSpell(String chinese, boolean ignoreNonChineseChar, boolean lowerCase) {
        return toFirstSpell(chinese, ignoreNonChineseChar, lowerCase, "");
    }

    default public String toFirstSpell(String chinese, boolean ignoreNonChineseChar) {
        return toFirstSpell(chinese, ignoreNonChineseChar, true, "");
    }

    default public String toFirstSpell(String chinese) {
        return toFirstSpell(chinese, true, true, "");
    }

    public String toSpell(String chinese, boolean ignoreNonChineseChar, boolean lowerCase, String separator);

    default public String toSpell(String chinese, boolean ignoreNonChineseChar, boolean lowerCase) {
        return toSpell(chinese, ignoreNonChineseChar, lowerCase, "");
    }

    default public String toSpell(String chinese, boolean ignoreNonChineseChar) {
        return toSpell(chinese, ignoreNonChineseChar, true, "");
    }

    default public String toSpell(String chinese) {
        return toSpell(chinese, true, true, "");
    }

}

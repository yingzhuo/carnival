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

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {

    private static final String CHINESE_REGEX = "[\u4e00-\u9fa5]";
    private static final String EMPTY_STRING = "";
    private static final String SPACE_STRING = " ";

    /**
     * 私有构造方法
     */
    private PinyinUtils() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------------

    /**
     * 生成中文拼音首字母
     *
     * @param chinese 中文字符串
     * @return 中文拼音首字母
     */
    public static String cn2FirstSpell(String chinese) {
        return cn2FirstSpell(chinese, true);
    }

    /**
     * 生成中文拼音首字母
     *
     * @param chinese              中文字符串
     * @param ignoreNonChineseChar 是否忽略非中文字符
     * @return 中文拼音首字母
     */
    public static String cn2FirstSpell(String chinese, boolean ignoreNonChineseChar) {
        return cn2FirstSpell(chinese, ignoreNonChineseChar, true);
    }

    /**
     * 生成中文拼音首字母
     *
     * @param chinese              中文字符串
     * @param ignoreNonChineseChar 是否忽略非中文字符
     * @param lowerCase            生成结果是否为小写字母
     * @return 中文拼音首字母
     */
    public static String cn2FirstSpell(String chinese, boolean ignoreNonChineseChar, boolean lowerCase) {
        return cn2FirstSpell(chinese, ignoreNonChineseChar, lowerCase, EMPTY_STRING);
    }

    /**
     * 生成中文拼音首字母
     *
     * @param chinese              中文字符串
     * @param ignoreNonChineseChar 是否忽略非中文字符
     * @param lowerCase            生成结果是否为小写字母
     * @param separator            分隔符
     * @return 中文拼音首字母
     */
    public static String cn2FirstSpell(String chinese, boolean ignoreNonChineseChar, boolean lowerCase, String separator) {

        if (chinese == null || chinese.isEmpty()) {
            return EMPTY_STRING;
        }

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);

        if (lowerCase) {
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        } else {
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        }

        StringBuilder result = new StringBuilder();

        char[] arr = chinese.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (!new String(new char[]{c}).matches(CHINESE_REGEX)) {
                if (!ignoreNonChineseChar) {
                    result.append(c);
                    if (i != arr.length - 1) {
                        result.append(separator);
                    }
                }
                continue;
            }
            try {
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if (pinyinArray != null && pinyinArray.length != 0) {
                    result.append(pinyinArray[0].charAt(0));
                    if (i != arr.length - 1) {
                        result.append(separator);
                    }
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }
        return result.toString().trim();
    }

    // -----------------------------------------------------------------------------------------------------------------------

    /**
     * 中文串换为拼音
     *
     * @param chinese 中文字符串
     * @return 拼音
     */
    public static String cn2Spell(String chinese) {
        return cn2Spell(chinese, true);
    }

    /**
     * 中文串换为拼音
     *
     * @param chinese              中文字符串
     * @param ignoreNonChineseChar 是否忽略非中文字符
     * @return 拼音
     */
    public static String cn2Spell(String chinese, boolean ignoreNonChineseChar) {
        return cn2Spell(chinese, ignoreNonChineseChar, true);
    }

    /**
     * 中文串换为拼音
     *
     * @param chinese              中文字符串
     * @param ignoreNonChineseChar 是否忽略非中文字符
     * @param lowerCase            生成结果是否为小写字母
     * @return 拼音
     */
    public static String cn2Spell(String chinese, boolean ignoreNonChineseChar, boolean lowerCase) {
        return cn2Spell(chinese, ignoreNonChineseChar, lowerCase, SPACE_STRING);
    }

    /**
     * 中文串换为拼音
     *
     * @param chinese              中文字符串
     * @param ignoreNonChineseChar 是否忽略非中文字符
     * @param lowerCase            生成结果是否为小写字母
     * @param separator            分隔符
     * @return 拼音
     */
    public static String cn2Spell(String chinese, boolean ignoreNonChineseChar, boolean lowerCase, String separator) {
        if (chinese == null || chinese.isEmpty()) {
            return EMPTY_STRING;
        }

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);

        if (lowerCase) {
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        } else {
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        }

        StringBuilder result = new StringBuilder();

        char[] arr = chinese.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (!new String(new char[]{c}).matches(CHINESE_REGEX)) {
                if (!ignoreNonChineseChar) {
                    result.append(c);
                    if (i != arr.length - 1) {
                        result.append(separator);
                    }
                }
                continue;
            }
            try {
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if (pinyinArray != null && pinyinArray.length != 0) {
                    result.append(pinyinArray[0].substring(0, pinyinArray[0].length() - 1));
                    if (i != arr.length - 1) {
                        result.append(separator);
                    }
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }

        return result.toString().trim();
    }
}

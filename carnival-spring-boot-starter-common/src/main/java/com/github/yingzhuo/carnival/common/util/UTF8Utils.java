/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.10.25
 */
public final class UTF8Utils {

    private UTF8Utils() {
    }

    public static String transformOctalToString(String s) {

        Objects.requireNonNull(s);

        if (!s.contains("\\")) {
            return s;
        }
        //不属于八进制内容的字符
        StringBuilder oldBuffer = new StringBuilder();
        //属于八进制的内容，转成十六进制后缓存在这里
        StringBuilder hexBuffer = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != '\\') {
                oldBuffer.append(c);
            }
            //反斜杠往后3个为一组，组成了一个八进制数。例如\20710,其实是207组成了一个八进制数
            else {
                char c1 = s.charAt(i + 1);
                char c2 = s.charAt(i + 2);
                char c3 = s.charAt(i + 3);
                i += 3;
                //将八进制转换为十进制，再转换为十六进制
                String hex = Integer.toHexString((Integer.valueOf("" + c1 + c2 + c3, 8)));
                //先缓存住，直到凑够三个字节
                hexBuffer.append(hex);
                String hexString = hexBuffer.toString();
                //utf8编码中，三个字节为一个汉字
                if (hexString.length() == 6) {
                    //凑够三个字节了，转成汉字后放入oldBuffer中
                    oldBuffer.append(hexStr2Str(hexString));
                    //凑够一个汉字了，清空缓存
                    hexBuffer = new StringBuilder();
                }
            }
        }
        return oldBuffer.toString();
    }

    /**
     * 十六进制转换字符串
     */
    private static String hexStr2Str(String hexStr) {
        String str = "0123456789abcdef";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }
}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.io.ini;

import com.github.yingzhuo.carnival.common.util.StringUtils;

/**
 * @author 应卓
 */
class IniUtils {

    /* 判断是否为非空行 */
    static boolean isNotBlank(String line) {
        return !StringUtils.isBlank(line);
    }

    /* 删除注释 */
    static String removeCommentsAndTrim(String line) {
        if (line == null) return "";
        if (line.startsWith("#")) return "";

        line = line.replaceAll("\\\\#", String.valueOf('\n'));

        int commentStart = line.indexOf('#');
        if (commentStart != -1) {

            if (line.charAt(commentStart - 1) != '\\') {
                line = line.substring(0, commentStart);
            }
        }

        return line.replace("\n", "\\#").trim();
    }

}

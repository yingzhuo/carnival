/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.validation.jsr310.password;

/**
 * 密码验证的复杂度
 *
 * @author 应卓
 */
public enum Complexity {

    /**
     * 无要求
     */
    NONE,

    /**
     * 数字 (0-9)
     */
    NUMERIC,

    /**
     * 字母 (a-z, A-Z)
     */
    ALPHABETIC,

    /**
     * 字母 + 数字 (a-z, A-Z, 0-9)
     */
    ALPHABETIC_AND_NUMERIC,

    /**
     * 字母 + 数字 + 特殊字符
     */
    ALPHABETIC_AND_NUMERIC_AND_SPECIAL_CHARS

}

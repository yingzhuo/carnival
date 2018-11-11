/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.gravatar;

/**
 * @author 应卓
 */
public enum Rating {

    GENERAL_AUDIENCE("g"),

    PARENTAL_GUIDANCE_SUGGESTED("pg"),

    RESTRICTED("r"),

    ADULT_ONLY("x");

    private final String key;

    private Rating(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
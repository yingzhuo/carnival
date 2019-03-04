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
public enum DefaultImage {

    HTTP_404("404"),

    MYSTERY_MAN("mm"),

    IDENTICON("identicon"),

    MONSTER("monsterid"),

    WAVATAR("wavatar"),

    RETRO("retro");

    private final String key;

    DefaultImage(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}

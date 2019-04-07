/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.tool.impl;

import com.github.yingzhuo.carnival.tool.KeyMapper;

import java.util.Objects;

/**
 * @author 应卓
 */
public class DefaultKeyMapper implements KeyMapper {

    private final String prefix;
    private final String suffix;

    public DefaultKeyMapper(String prefix) {
        this(prefix, "");
    }

    public DefaultKeyMapper(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public String map(CharSequence key) {
        Objects.requireNonNull(key);
        return prefix + key.toString() + suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.refuse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 应卓
 * @see RefuseConfigLoader
 */
final public class RefuseConfig {

    private static final String EMPTY_REASON = "";

    private final Map<String, String> infos = new HashMap<>();
    private String defaultReason = null;

    public RefuseConfig() {
        this(null);
    }

    public RefuseConfig(String defaultReason) {
        this.defaultReason = defaultReason;
    }

    public void addConfig(String path, String reason) {
        Objects.requireNonNull(path, (String) null);
        infos.put(path, reason);
    }

    public void addConfig(String path) {
        addConfig(path, EMPTY_REASON);
    }

    public void removeConfig(String path) {
        infos.remove(path);
    }

    public void clear() {
        infos.clear();
    }

    public boolean isEmpty() {
        return infos.isEmpty();
    }

    public int size() {
        return infos.size();
    }

    public String getDefaultReason() {
        return defaultReason;
    }

    public void setDefaultReason(String defaultReason) {
        this.defaultReason = defaultReason;
    }

    public Map<String, String> toMap() {
        return Collections.unmodifiableMap(infos);
    }

}

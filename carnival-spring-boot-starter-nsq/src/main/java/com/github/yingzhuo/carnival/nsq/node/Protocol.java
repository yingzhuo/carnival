/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq.node;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author 应卓
 * @since 1.3.1
 */
public enum Protocol {

    HTTP("http"), HTTPS("https");

    @JsonValue
    private String value;

    Protocol(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

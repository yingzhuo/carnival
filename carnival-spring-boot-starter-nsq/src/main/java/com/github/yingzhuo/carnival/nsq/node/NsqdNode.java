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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.3.1
 */
@Getter
@Setter
@ToString
public class NsqdNode implements Serializable {

    @JsonProperty("broadcast_address")
    private String host;

    @JsonProperty("http_port")
    private int httpPort;

    @JsonProperty("tcp_port")
    private int tcpPort;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NsqdNode nsqdNode = (NsqdNode) o;
        return httpPort == nsqdNode.httpPort &&
                tcpPort == nsqdNode.tcpPort &&
                Objects.equals(host, nsqdNode.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, httpPort, tcpPort);
    }
}

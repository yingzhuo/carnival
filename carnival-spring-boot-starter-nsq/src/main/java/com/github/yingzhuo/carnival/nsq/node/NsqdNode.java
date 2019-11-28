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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.3.1
 */
@Getter
@Setter
@ToString
public class NsqdNode {

    private Protocol protocol = Protocol.HTTP;
    private String host;
    private int port = 4151;

    public NsqdNode() {
    }

    public NsqdNode(Protocol protocol, String host, int port) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NsqdNode nsqdNode = (NsqdNode) o;
        return port == nsqdNode.port &&
                protocol == nsqdNode.protocol &&
                Objects.equals(host, nsqdNode.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, host, port);
    }
}

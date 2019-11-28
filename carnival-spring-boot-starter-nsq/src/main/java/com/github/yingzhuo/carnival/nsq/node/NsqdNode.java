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
    private String nsqdHost;
    private int nsqdPort = 4151;

    public NsqdNode() {
    }

    public NsqdNode(Protocol protocol, String nsqdHost, int nsqdPort) {
        this.protocol = protocol;
        this.nsqdHost = nsqdHost;
        this.nsqdPort = nsqdPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NsqdNode nsqdNode = (NsqdNode) o;
        return nsqdPort == nsqdNode.nsqdPort &&
                protocol == nsqdNode.protocol &&
                Objects.equals(nsqdHost, nsqdNode.nsqdHost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, nsqdHost, nsqdPort);
    }

}

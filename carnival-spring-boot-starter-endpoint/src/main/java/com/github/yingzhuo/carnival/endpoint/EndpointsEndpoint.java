/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.endpoint;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.Endpoint;

import java.util.List;

public class EndpointsEndpoint extends AbstractEndpoint<List<Endpoint>> {

    private final List<Endpoint> endpoints;

    public EndpointsEndpoint(List<Endpoint> endpoints) {
        super("endpoints");
        this.setEnabled(true);
        this.setSensitive(false);
        this.endpoints = endpoints;
        this.endpoints.add(this);
    }

    @Override
    public List<Endpoint> invoke() {
        return this.endpoints;
    }

}

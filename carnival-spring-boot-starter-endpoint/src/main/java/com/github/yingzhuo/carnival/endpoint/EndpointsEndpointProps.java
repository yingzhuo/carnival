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

import com.github.yingzhuo.carnival.endpoint.support.AbstractEndpointProps;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("endpoint.endpoints")
public class EndpointsEndpointProps extends AbstractEndpointProps {

    public EndpointsEndpointProps() {
        super.setId("endpoints");
    }

}

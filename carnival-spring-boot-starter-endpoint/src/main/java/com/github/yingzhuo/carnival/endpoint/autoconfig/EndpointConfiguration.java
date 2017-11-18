/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.endpoint.autoconfig;

import com.github.yingzhuo.carnival.endpoint.EndpointsEndpoint;
import com.github.yingzhuo.carnival.endpoint.EndpointsEndpointProps;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

@EnableConfigurationProperties(EndpointsEndpointProps.class)
public class EndpointConfiguration {

    @Bean
    public EndpointsEndpoint endpointsEndpoint(List<Endpoint> endpoints, EndpointsEndpointProps props) {
        EndpointsEndpoint bean = new EndpointsEndpoint(endpoints);
        bean.setEnabled(props.isEnabled());
        bean.setId(props.getId());
        bean.setSensitive(props.isSensitive());
        return bean;
    }

}

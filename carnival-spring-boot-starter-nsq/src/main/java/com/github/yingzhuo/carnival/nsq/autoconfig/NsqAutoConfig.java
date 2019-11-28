/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq.autoconfig;

import com.github.yingzhuo.carnival.nsq.NsqdClient;
import com.github.yingzhuo.carnival.nsq.NsqdNodeSelector;
import com.github.yingzhuo.carnival.nsq.impl.DefaultNsqdClient;
import com.github.yingzhuo.carnival.nsq.node.NsqdNode;
import com.github.yingzhuo.carnival.nsq.props.NsqProps;
import com.github.yingzhuo.carnival.nsq.selector.RandomNsqdNodeSelector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @since 1.3.1
 */
@EnableConfigurationProperties(NsqProps.class)
@ConditionalOnProperty(prefix = "carnival.nsq", name = "enabled", havingValue = "true", matchIfMissing = true)
public class NsqAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public NsqdNodeSelector nsqdNodeSelector() {
        return new RandomNsqdNodeSelector();
    }

    @Bean
    @ConditionalOnMissingBean
    public NsqdClient nsqdClient(NsqProps props, NsqdNodeSelector selector) {
        Set<NsqdNode> nodes = props.getNsqdNodes().parallelStream().collect(Collectors.toSet());
        return new DefaultNsqdClient(nodes, selector);
    }

}

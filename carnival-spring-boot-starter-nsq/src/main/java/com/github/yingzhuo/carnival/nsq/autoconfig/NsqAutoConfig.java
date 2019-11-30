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

import com.github.yingzhuo.carnival.nsq.config.LookupConfig;
import com.github.yingzhuo.carnival.nsq.config.ProducerConfig;
import com.github.yingzhuo.carnival.nsq.lookup.Finder;
import com.github.yingzhuo.carnival.nsq.lookup.FinderImpl;
import com.github.yingzhuo.carnival.nsq.producer.DirectProducerImpl;
import com.github.yingzhuo.carnival.nsq.producer.Producer;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.3.1
 */
@Configuration
@EnableConfigurationProperties(NsqAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.nsq", name = "enabled", havingValue = "true", matchIfMissing = true)
public class NsqAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "carnival.nsq.finder", name = "enabled", havingValue = "true", matchIfMissing = true)
    public Finder finder(Props props) {
        val bean = new FinderImpl();
        bean.setNsqLookupdNodeList(props.getFinder().getNodes());
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "carnival.nsq.producer", name = "enabled", havingValue = "true", matchIfMissing = true)
    public Producer producer(Props props) {
        return new DirectProducerImpl(props.getProducer().getNode());
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.nsq")
    static class Props {
        private boolean enabled = true;
        private FinderProps finder = new FinderProps();
        private ProducerProps producer = new ProducerProps();
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.nsq.finder")
    static class FinderProps {
        private boolean enabled = false;
        private List<LookupConfig> nodes = new ArrayList<>();
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.nsq.producer")
    static class ProducerProps {
        private boolean enabled = false;
        private ProducerConfig node = new ProducerConfig();
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.actuator.autoconfig;

import com.github.yingzhuo.carnival.actuator.endpoint.HelpEndpoint;
import com.github.yingzhuo.carnival.actuator.endpoint.NoteEndpoint;
import com.github.yingzhuo.carnival.common.condition.ConditionalOnAnyResource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.6.5
 */
@EnableConfigurationProperties(ActuatorCoreConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.actuator", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ActuatorCoreConfig {

    @Bean
    @ConditionalOnAnyResource(resources = {
            "classpath:help.md",
            "classpath:HELP.md",
            "classpath:META-INF/help.md",
            "classpath:META-INF/HELP.md"
    })
    public HelpEndpoint helpEndpoint() {
        return new HelpEndpoint();
    }

    @Bean
    @ConditionalOnAnyResource(resources = {
            "classpath:note.md",
            "classpath:NOTE.md",
            "classpath:META-INF/note.md",
            "classpath:META-INF/NOTE.md"
    })
    public NoteEndpoint noteEndpoint() {
        return new NoteEndpoint();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.actuator")
    static class Props {
        private boolean enabled = true;
    }

}

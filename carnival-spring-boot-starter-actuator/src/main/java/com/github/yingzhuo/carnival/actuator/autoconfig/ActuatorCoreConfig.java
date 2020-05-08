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

import com.github.yingzhuo.carnival.actuator.endpoint.ManifestEndpoint;
import com.github.yingzhuo.carnival.actuator.endpoint.NoteEndpoint;
import com.github.yingzhuo.carnival.common.condition.ConditionalOnAnyResource;
import com.github.yingzhuo.carnival.common.condition.ConditionalOnManifest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.6.5
 */
@EnableConfigurationProperties(ActuatorCoreConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.actuator", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ActuatorCoreConfig {

    @Bean
    @ConditionalOnAnyResource(resources = {
            "classpath:/note.md",
            "classpath:/NOTE.md",
            "classpath:/META-INF/note.md",
            "classpath:/META-INF/NOTE.md"
    })
    public NoteEndpoint noteEndpoint() {
        return new NoteEndpoint();
    }

    @Bean
    @ConditionalOnManifest
    public ManifestEndpoint manifestEndpoint() {
        return new ManifestEndpoint();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.actuator")
    static class Props implements Serializable {
        private boolean enabled = true;
    }


}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 应卓
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ConfigurationProperties(prefix = "carnival.nsq.producer.node")
public class ProducerConfig {

    private Protocol protocol = Protocol.HTTP;
    private String host;
    private int httpPort = 4151;
    private int tcpPort = 4150;

}

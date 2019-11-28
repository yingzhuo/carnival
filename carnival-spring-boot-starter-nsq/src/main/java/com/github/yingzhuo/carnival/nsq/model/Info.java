/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.3.1
 */
@Getter
@Setter
@ToString
public class Info implements Serializable {

    @JsonProperty("version")
    private String version;

    @JsonProperty("broadcast_address")
    private String broadcastAddress;

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("http-port")
    private int httpPort;

    @JsonProperty("tcp-port")
    private int tcpPort;

    @JsonProperty("start_time")
    private long startTime;

}

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
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Producer {

    @JsonProperty("remote_address")
    private String remoteAddress;

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("broadcast_address")
    private String broadcastAddress;

    @JsonProperty("tcp_port")
    private int tcpPort;

    @JsonProperty("http_port")
    private int httpPort;

    @JsonProperty("tombstones")
    private List<Boolean> tombstones = new ArrayList<>();

    @JsonProperty("topics")
    private List<String> topics = new ArrayList<>();

}
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

/**
 * @author 应卓
 * @since 1.3.1
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LookupConfig {

    private Protocol protocol = Protocol.HTTP;
    private String host;
    private int httpPort;

}

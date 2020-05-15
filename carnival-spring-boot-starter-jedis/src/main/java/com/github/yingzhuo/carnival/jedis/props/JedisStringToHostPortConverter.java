/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jedis.props;

import org.springframework.core.convert.converter.Converter;
import redis.clients.jedis.HostAndPort;

/**
 * @author 应卓
 * @since 1.6.7
 */
public class JedisStringToHostPortConverter implements Converter<String, HostAndPort> {

    @Override
    public HostAndPort convert(String source) {
        return HostAndPort.parseString(source.trim());
    }

}

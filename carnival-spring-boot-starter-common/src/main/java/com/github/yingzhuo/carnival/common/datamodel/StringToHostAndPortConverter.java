/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.datamodel;

import com.google.common.net.HostAndPort;
import org.springframework.core.convert.converter.Converter;

/**
 * @author 应卓
 * @since 1.5.2
 */
public class StringToHostAndPortConverter implements Converter<String, HostAndPort> {

    @Override
    public HostAndPort convert(String source) {
        return HostAndPort.fromString(source);
    }

}

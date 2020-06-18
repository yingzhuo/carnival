/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign;

import feign.Capability;
import feign.Contract;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;

import java.util.List;

/**
 * @author 应卓
 * @since 1.6.19
 */
public interface Configuration {

    public default Contract getContract() {
        return null;
    }

    public default Encoder getEncoder() {
        return null;
    }

    public default Decoder getDecoder() {
        return null;
    }

    public default ErrorDecoder getErrorDecoder() {
        return null;
    }

    public default List<Capability> getCapabilities() {
        return null;
    }

    public default boolean decode404() {
        return false;
    }

    public default List<RequestInterceptor> getRequestInterceptors() {
        return null;
    }

    public default Retryer getRetryer() {
        return null;
    }

}

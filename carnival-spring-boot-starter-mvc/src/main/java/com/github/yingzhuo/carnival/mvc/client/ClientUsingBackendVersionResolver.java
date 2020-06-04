/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.client;

import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author 应卓
 * @since 1.6.14
 */
@FunctionalInterface
public interface ClientUsingBackendVersionResolver {

    public String resolveClientUsingBackendVersion(NativeWebRequest request);

}

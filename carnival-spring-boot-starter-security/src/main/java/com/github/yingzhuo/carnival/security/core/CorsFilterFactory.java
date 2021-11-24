/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.core;

/**
 * @author 应卓
 * @author 朱嘉杰
 * @see LoggingFilter
 * @see org.springframework.context.ApplicationContext
 * @since 1.10.38
 */
@FunctionalInterface
public interface CorsFilterFactory {

    public CorsFilter create();

}

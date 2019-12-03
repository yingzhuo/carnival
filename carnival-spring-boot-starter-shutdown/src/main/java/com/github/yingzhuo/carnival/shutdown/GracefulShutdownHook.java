/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.shutdown;

import org.springframework.core.Ordered;

/**
 * @author 应卓
 * @since 1.3.1
 */
public interface GracefulShutdownHook extends Ordered {

    public abstract void onShutdown() throws Throwable;

    public default int getOrder() {
        return 0;
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.event;

import org.springframework.core.Ordered;

/**
 * @author 应卓
 * @since 1.6.15
 */
@FunctionalInterface
public interface ApplicationInitializer extends Ordered {

    public void execute();

    @Override
    default int getOrder() {
        return 0;
    }

}

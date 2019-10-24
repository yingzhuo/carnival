/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.hook;

import org.springframework.core.Ordered;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author 应卓
 */
public interface BeforeHook extends Ordered {

    public void execute(NativeWebRequest request);

    @Override
    public default int getOrder() {
        return 0;
    }

    public default BeforeHook link(BeforeHook that) {
        return (request) -> {
            this.execute(request);
            that.execute(request);
        };
    }

}

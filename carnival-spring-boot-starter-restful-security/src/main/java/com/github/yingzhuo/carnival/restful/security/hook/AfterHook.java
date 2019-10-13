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

import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import org.springframework.core.Ordered;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author 应卓
 * @since 1.2.0
 */
public interface AfterHook extends Ordered {

    public void execute(NativeWebRequest request, Token token, UserDetails userDetails);

    @Override
    public default int getOrder() {
        return 0;
    }

    public default AfterHook link(AfterHook that) {
        return (request, token, userDetails) -> {
            this.execute(request, token, userDetails);
            that.execute(request, token, userDetails);
        };
    }

}

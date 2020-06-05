/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.responsibility;

import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author 应卓
 */
@FunctionalInterface
public interface Responsibility {

    public void execute(NativeWebRequest request);

    public default String[] excludePatterns() {
        return new String[0];
    }

}

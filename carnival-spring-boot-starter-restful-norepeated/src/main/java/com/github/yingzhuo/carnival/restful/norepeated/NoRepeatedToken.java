/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.norepeated;

import com.github.yingzhuo.carnival.restful.norepeated.factory.NoRepeatedTokenFactory;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 * @since 1.6.7
 */
public final class NoRepeatedToken {

    public static String create() {
        return SpringUtils.getBean(NoRepeatedTokenFactory.class).create();
    }

    private NoRepeatedToken() {
    }

}

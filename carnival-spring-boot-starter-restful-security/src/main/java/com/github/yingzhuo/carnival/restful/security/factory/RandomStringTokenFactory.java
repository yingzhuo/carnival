/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.factory;

import com.github.yingzhuo.codegen4j.UUIDs;

/**
 * @author 应卓
 * @since 1.8.2
 */
public class RandomStringTokenFactory implements StringTokenFactory<Object> {

    @Override
    public String create(Object user) {
        return UUIDs.next();
    }

}

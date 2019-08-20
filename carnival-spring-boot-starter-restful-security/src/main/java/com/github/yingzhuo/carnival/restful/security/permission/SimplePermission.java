/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.permission;

/**
 * @author 应卓
 */
public class SimplePermission implements Permission {

    private final String name;

    public SimplePermission(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

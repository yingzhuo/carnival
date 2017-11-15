/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.impl;

import com.github.yingzhuo.carnival.restful.security.Role;
import lombok.ToString;
import org.springframework.util.Assert;

@ToString
public final class SimpleRole implements Role {

    private final String name;

    public SimpleRole(String name) {
        Assert.hasText(name, null);
        this.name = name;
    }

    @Override
    public String getRoleName() {
        return name;
    }

}

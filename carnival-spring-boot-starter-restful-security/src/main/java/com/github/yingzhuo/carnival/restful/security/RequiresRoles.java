/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security;

import com.github.yingzhuo.carnival.restful.security.annotation.Requires;
import com.github.yingzhuo.carnival.restful.security.auth.RequiresRolesAuthComponent;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @see RequiresRolesAuthComponent
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Requires(RequiresRolesAuthComponent.class)
public @interface RequiresRoles {

    public String[] value();

    public Logical logical() default Logical.ANY;

}

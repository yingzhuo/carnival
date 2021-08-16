/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.annotation;

import com.github.yingzhuo.carnival.security.userdetails.UserDetailsPlus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @see UserDetails
 * @see UserDetails#getUsername()
 * @see UserDetailsPlus
 * @since 1.10.2
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@AuthenticationPrincipal(expression = "#root.username")
public @interface CurrentUserUsername {
}

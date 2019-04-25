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

import com.github.yingzhuo.carnival.restful.security.annotation.AuthenticationComponent;
import com.github.yingzhuo.carnival.restful.security.annotation.Requires;
import com.github.yingzhuo.carnival.restful.security.exception.RestfulSecurityException;
import com.github.yingzhuo.carnival.restful.security.exception.UserDetailsGenderException;
import com.github.yingzhuo.carnival.restful.security.userdetails.Gender;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.var;

import java.lang.annotation.*;

/**
 * @author 应卓
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Requires(RequiresMale.AuthComponent.class)
public @interface RequiresMale {

    public String errorMessage() default ":::<NO MESSAGE>:::";

    public static class AuthComponent implements AuthenticationComponent<RequiresMale> {

        @Override
        public void authenticate(UserDetails userDetails, RequiresMale annotation) throws RestfulSecurityException {
            if (userDetails.getGender() != Gender.MALE) {
                throw new UserDetailsGenderException(getMessage(annotation));
            }
        }

        private String getMessage(RequiresMale annotation) {
            var msg = annotation.errorMessage();
            if (":::<NO MESSAGE>:::".equals(msg)) {
                msg = null;
            }
            return msg;
        }
    }

}

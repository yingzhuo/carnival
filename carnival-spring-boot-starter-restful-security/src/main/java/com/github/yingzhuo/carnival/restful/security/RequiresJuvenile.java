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
import com.github.yingzhuo.carnival.restful.security.core.CheckUtils;
import com.github.yingzhuo.carnival.restful.security.exception.AuthorizationException;
import com.github.yingzhuo.carnival.restful.security.exception.RestfulSecurityException;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;
import org.apache.commons.lang3.time.DateUtils;

import java.lang.annotation.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 应卓
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Requires(RequiresJuvenile.AuthComponent.class)
public @interface RequiresJuvenile {

    public int ageOfAdult() default 18;

    public String errorMessage() default ":::<NO MESSAGE>:::";

    public static class AuthComponent implements AuthenticationComponent<RequiresJuvenile> {

        @Override
        public void authenticate(UserDetails userDetails, RequiresJuvenile annotation) throws RestfulSecurityException {

            if (userDetails == null || userDetails.getDateOfBirth() == null) {
                throw new AuthorizationException(CheckUtils.getMessage(annotation.errorMessage()));
            }

            val t = DateUtils.truncate(new Date(), Calendar.DATE);
            val a = DateUtils.addYears(userDetails.getDateOfBirth(), annotation.ageOfAdult());

            if (t.after(a)) {
                throw new AuthorizationException(CheckUtils.getMessage(annotation.errorMessage()));
            }
        }
    }

}

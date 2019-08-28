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
import com.github.yingzhuo.carnival.restful.security.exception.*;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;
import org.apache.commons.lang3.time.DateUtils;

import java.lang.annotation.*;
import java.util.Calendar;
import java.util.Date;

import static com.github.yingzhuo.carnival.restful.security.MessageUtils.getMessage;

/**
 * @author 应卓
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Requires(RequiresAdult.AuthComponent.class)
public @interface RequiresAdult {

    public int ageOfAdult() default 18;

    public String errorMessage() default ":::<NO MESSAGE>:::";

    public static class AuthComponent implements AuthenticationComponent<RequiresAdult> {

        @Override
        public void authenticate(UserDetails userDetails, RequiresAdult annotation) throws RestfulSecurityException {

            if (userDetails == null) {
                throw new AuthenticationException(getMessage(annotation.errorMessage()));
            }

            if (userDetails.isLocked()) {
                throw new UserDetailsLockedException(getMessage(annotation.errorMessage()));
            }

            if (userDetails.isExpired()) {
                throw new UserDetailsExpiredException(getMessage(annotation.errorMessage()));
            }

            if (userDetails.getDateOfBirth() == null) {
                throw new AuthorizationException(getMessage(annotation.errorMessage()));
            }

            val t = DateUtils.truncate(new Date(), Calendar.DATE);
            val a = DateUtils.addYears(userDetails.getDateOfBirth(), annotation.ageOfAdult());

            if (t.before(a)) {
                throw new AuthorizationException(getMessage(annotation.errorMessage()));
            }
        }
    }

}

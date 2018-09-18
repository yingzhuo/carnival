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
import com.github.yingzhuo.carnival.restful.security.exception.LimitedAdultContentException;
import com.github.yingzhuo.carnival.restful.security.exception.RestfulSecurityException;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;
import lombok.var;
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
@Requires(RequiresAdult.AuthComponent.class)
public @interface RequiresAdult {

    public int ageOfAdult() default 18;

    public String errorMessage() default ":::<NO MESSAGE>:::";

    public static class AuthComponent implements AuthenticationComponent {

        @Override
        public void authenticate(UserDetails userDetails, Annotation annotation) throws RestfulSecurityException {
            val requiresAdult = (RequiresAdult) annotation;

            if (userDetails == null || userDetails.getDateOfBirth() == null) {
                throw new LimitedAdultContentException(getMessage(requiresAdult));
            }

            val t = DateUtils.truncate(new Date(), Calendar.DATE);
            val a = DateUtils.addYears(userDetails.getDateOfBirth(), requiresAdult.ageOfAdult());

            if (t.before(a)) {
                throw new LimitedAdultContentException(getMessage(requiresAdult));
            }
        }

        private String getMessage(RequiresAdult annotation) {
            var msg = annotation.errorMessage();
            if (":::<NO MESSAGE>:::".equals(msg)) {
                msg = null;
            }
            return msg;
        }
    }

}

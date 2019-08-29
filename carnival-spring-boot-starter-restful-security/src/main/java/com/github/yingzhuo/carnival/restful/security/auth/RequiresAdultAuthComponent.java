/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.auth;

import com.github.yingzhuo.carnival.restful.security.RequiresAdult;
import com.github.yingzhuo.carnival.restful.security.annotation.AuthenticationComponent;
import com.github.yingzhuo.carnival.restful.security.exception.*;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.val;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

import static com.github.yingzhuo.carnival.restful.security.auth.MessageUtils.getMessage;

/**
 * @author 应卓
 * @since 1.1.5
 */
public class RequiresAdultAuthComponent implements AuthenticationComponent<RequiresAdult> {

    @Override
    public void authenticate(Token token, UserDetails userDetails, RequiresAdult annotation) throws RestfulSecurityException {

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

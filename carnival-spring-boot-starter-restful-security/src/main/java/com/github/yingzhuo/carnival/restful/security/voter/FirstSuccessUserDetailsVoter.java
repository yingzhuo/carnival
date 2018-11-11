/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.voter;

import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 */
public class FirstSuccessUserDetailsVoter implements UserDetailsVoter {

    @Override
    public Optional<UserDetails> vote(List<AuthenticationResult> authenticationResults) {

        if (authenticationResults == null || authenticationResults.isEmpty()) {
            return Optional.empty();
        }

        for (AuthenticationResult result : authenticationResults) {
            if (result.isSuccess()) {
                return Optional.of(result.getUserDetails());
            }
        }

        return Optional.empty();
    }

}

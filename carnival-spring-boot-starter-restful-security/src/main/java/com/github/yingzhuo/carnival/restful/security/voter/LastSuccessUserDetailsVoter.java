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
import lombok.val;

import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 */
public class LastSuccessUserDetailsVoter implements UserDetailsVoter {

    @Override
    public Optional<UserDetails> vote(List<AuthenticationResult> authenticationResults) {

        if (authenticationResults == null || authenticationResults.isEmpty()) {
            return Optional.empty();
        }

        for (int i = authenticationResults.size() - 1; i >= 0; i--) {
            val ar = authenticationResults.get(i);
            if (ar.isSuccess()) {
                return Optional.of(ar.getUserDetails());
            }
        }

        return Optional.empty();
    }

}

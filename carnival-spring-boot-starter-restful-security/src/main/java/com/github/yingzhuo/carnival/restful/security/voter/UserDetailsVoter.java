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
public interface UserDetailsVoter {

    public Optional<UserDetails> vote(List<AuthenticationResult> authenticationResults);

}

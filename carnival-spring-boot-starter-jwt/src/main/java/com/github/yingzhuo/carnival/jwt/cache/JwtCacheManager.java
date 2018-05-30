package com.github.yingzhuo.carnival.jwt.cache;

import com.github.yingzhuo.carnival.restful.security.UserDetails;
import com.github.yingzhuo.carnival.restful.security.impl.StringToken;

import java.util.Optional;

/**
 * @author 应卓
 */
public interface JwtCacheManager {

    public Optional<UserDetails> getUserDetails(StringToken token);

    public void saveUserDetails(StringToken token, UserDetails userDetails);

}

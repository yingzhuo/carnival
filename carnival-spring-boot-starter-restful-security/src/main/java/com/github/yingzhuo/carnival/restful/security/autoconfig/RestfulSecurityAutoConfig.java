/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.autoconfig;

import com.github.yingzhuo.carnival.restful.security.auth.*;
import com.github.yingzhuo.carnival.restful.security.blacklist.TokenBlacklistManager;
import com.github.yingzhuo.carnival.restful.security.hook.AfterHook;
import com.github.yingzhuo.carnival.restful.security.hook.BeforeHook;
import com.github.yingzhuo.carnival.restful.security.hook.ExceptionHook;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@Slf4j
@ConditionalOnWebApplication
public class RestfulSecurityAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public TokenBlacklistManager tokenBlackListManager() {
        return new TokenBlacklistManager() {
            @Override
            public void save(Token token) {
                // NOP
            }

            @Override
            public boolean isBlacklisted(Token token) {
                return false;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public BeforeHook beforeHook() {
        return request -> {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public AfterHook afterHook() {
        return (request, token, userDetails) -> {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ExceptionHook exceptionHook() {
        return (request, token, ex) -> {
        };
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Bean
    public RequiresAuthenticationAuthComponent requiresAuthenticationAuthComponent() {
        return new RequiresAuthenticationAuthComponent();
    }

    @Bean
    public RequiresRolesAuthComponent requiresRolesAuthComponent() {
        return new RequiresRolesAuthComponent();
    }

    @Bean
    public RequiresPermissionsAuthComponent requiresPermissionsAuthComponent() {
        return new RequiresPermissionsAuthComponent();
    }

    @Bean
    public RequiresGuestAuthComponent requiresGuestAuthComponent() {
        return new RequiresGuestAuthComponent();
    }

    @Bean
    public RequiresTokenAuthComponent requiresTokenAuthenticationComponent() {
        return new RequiresTokenAuthComponent();
    }

    @Bean
    public RequiresIdAuthComponent requiresIdAuthenticationComponent() {
        return new RequiresIdAuthComponent();
    }

    @Bean
    public RequiresUsernameAuthComponent requiresUserAuthenticationComponent() {
        return new RequiresUsernameAuthComponent();
    }

    @Bean
    public RequiresAdminAuthComponent requiresAdminAuthComponent() {
        return new RequiresAdminAuthComponent();
    }

}

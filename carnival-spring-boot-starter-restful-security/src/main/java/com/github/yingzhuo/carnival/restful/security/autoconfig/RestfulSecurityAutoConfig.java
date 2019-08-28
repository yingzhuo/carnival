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

import com.github.yingzhuo.carnival.restful.security.*;
import com.github.yingzhuo.carnival.restful.security.blacklist.NopTokenBlacklistManager;
import com.github.yingzhuo.carnival.restful.security.blacklist.TokenBlacklistManager;
import com.github.yingzhuo.carnival.restful.security.cache.CacheManager;
import com.github.yingzhuo.carnival.restful.security.cache.NopCacheManager;
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

    @ConditionalOnMissingBean
    @Bean(name = "restfulSecurityCacheManager")
    public CacheManager cacheManager() {
        return new NopCacheManager();
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenBlacklistManager tokenBlackListManager() {
        return new NopTokenBlacklistManager();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Bean
    public RequiresToken.AuthComponent requiresTokenAuthenticationComponent() {
        return new RequiresToken.AuthComponent();
    }

    @Bean
    public RequiresId.AuthComponent requiresIdAuthenticationComponent() {
        return new RequiresId.AuthComponent();
    }

    @Bean
    public RequiresUsername.AuthComponent requiresUserAuthenticationComponent() {
        return new RequiresUsername.AuthComponent();
    }

    @Bean
    public RequiresEmail.AuthComponent requiresEmailAuthenticationComponent() {
        return new RequiresEmail.AuthComponent();
    }

    @Bean
    public RequiresAdult.AuthComponent requiresAdultAuthenticationComponent() {
        return new RequiresAdult.AuthComponent();
    }

    @Bean
    public RequiresRoot.AuthComponent requiresRootAuthenticationComponent() {
        return new RequiresRoot.AuthComponent();
    }

    @Bean
    public RequiresNotRoot.AuthComponent requiresNotRootAuthenticationComponent() {
        return new RequiresNotRoot.AuthComponent();
    }

    @Bean
    public RequiresAuthentication.AuthComponent requiresAuthenticationAuthenticationComponent() {
        return new RequiresAuthentication.AuthComponent();
    }

    @Bean
    public RequiresGuest.AuthComponent requiresGuestAuthenticationComponent() {
        return new RequiresGuest.AuthComponent();
    }

    @Bean
    public RequiresRoles.AuthComponent requiresRolesAuthenticationComponent() {
        return new RequiresRoles.AuthComponent();
    }

    @Bean
    public RequiresPermissions.AuthComponent requiresPermissionsAuthenticationComponent() {
        return new RequiresPermissions.AuthComponent();
    }

}

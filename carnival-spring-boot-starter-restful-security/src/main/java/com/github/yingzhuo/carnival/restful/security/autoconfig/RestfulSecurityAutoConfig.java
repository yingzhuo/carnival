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
import org.springframework.context.annotation.Primary;

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
    @Primary
    public RequiresId.AuthComponent requiresIdAuthenticationComponent() {
        return new RequiresId.AuthComponent();
    }

    @Bean
    @Primary
    public RequiresUsername.AuthComponent requiresUserAuthenticationComponent() {
        return new RequiresUsername.AuthComponent();
    }

    @Bean
    @Primary
    public RequiresEmailAddress.AuthComponent requiresEmailAddressAuthenticationComponent() {
        return new RequiresEmailAddress.AuthComponent();
    }

    @Bean
    @Primary
    public RequiresAdult.AuthComponent requiresAdultAuthenticationComponent() {
        return new RequiresAdult.AuthComponent();
    }

    @Bean
    @Primary
    public RequiresRoot.AuthComponent requiresRootAuthenticationComponent() {
        return new RequiresRoot.AuthComponent();
    }

    @Bean
    @Primary
    public RequiresNotRoot.AuthComponent requiresNotRootAuthenticationComponent() {
        return new RequiresNotRoot.AuthComponent();
    }

    @Bean
    @Primary
    public RequiresAuthentication.AuthComponent requiresAuthenticationAuthenticationComponent() {
        return new RequiresAuthentication.AuthComponent();
    }

    @Bean
    @Primary
    public RequiresGuest.AuthComponent requiresGuestAuthenticationComponent() {
        return new RequiresGuest.AuthComponent();
    }

    @Bean
    @Primary
    public RequiresRoles.AuthComponent requiresRolesAuthenticationComponent() {
        return new RequiresRoles.AuthComponent();
    }

    @Bean
    @Primary
    public RequiresPermissions.AuthComponent requiresPermissionsAuthenticationComponent() {
        return new RequiresPermissions.AuthComponent();
    }

}

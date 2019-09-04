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
import com.github.yingzhuo.carnival.restful.security.blacklist.NopTokenBlacklistManager;
import com.github.yingzhuo.carnival.restful.security.blacklist.TokenBlacklistManager;
import com.github.yingzhuo.carnival.restful.security.cache.CacheManager;
import com.github.yingzhuo.carnival.restful.security.cache.NopCacheManager;
import com.github.yingzhuo.carnival.restful.security.refuse.NopRefuseManager;
import com.github.yingzhuo.carnival.restful.security.refuse.RefuseManager;
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

    @Bean
    @ConditionalOnMissingBean
    public RefuseManager refuseManager() {
        return new NopRefuseManager();
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
    public RequiresEmailAuthComponent requiresEmailAuthenticationComponent() {
        return new RequiresEmailAuthComponent();
    }

    @Bean
    public RequiresAdultAuthComponent requiresAdultAuthenticationComponent() {
        return new RequiresAdultAuthComponent();
    }

    @Bean
    public RequiresAdminAuthComponent requiresAdminAuthComponent() {
        return new RequiresAdminAuthComponent();
    }

    @Bean
    public RequiresGenderAuthComponent requiresGenderAuthComponent() {
        return new RequiresGenderAuthComponent();
    }

    @Bean
    public RequiresAnyRoleAuthComponent requiresAnyRoleAuthComponent() {
        return new RequiresAnyRoleAuthComponent();
    }

    @Bean
    public RequiresAllRolesAuthComponent requiresAllRolesAuthComponent() {
        return new RequiresAllRolesAuthComponent();
    }

    @Bean
    public RequiresAnyPermissionAuthComponent requiresAnyPermissionAuthComponent() {
        return new RequiresAnyPermissionAuthComponent();
    }

    @Bean
    public RequiresAllPermissionsAuthComponent requiresAllPermissionsAuthComponent() {
        return new RequiresAllPermissionsAuthComponent();
    }

}

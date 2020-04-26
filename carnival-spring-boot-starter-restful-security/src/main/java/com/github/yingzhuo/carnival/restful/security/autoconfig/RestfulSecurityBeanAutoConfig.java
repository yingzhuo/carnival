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
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @author 应卓
 */
@Lazy(false)
@ConditionalOnWebApplication
public class RestfulSecurityBeanAutoConfig {

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

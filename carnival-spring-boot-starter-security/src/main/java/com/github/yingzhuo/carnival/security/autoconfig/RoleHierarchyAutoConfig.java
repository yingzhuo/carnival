/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.autoconfig;

import com.github.yingzhuo.carnival.common.condition.ConditionalOnResource;
import com.github.yingzhuo.carnival.common.io.ResourceOptional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

/**
 * @author 应卓
 * @since 1.10.13
 */
class RoleHierarchyAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnResource(
            value = {
                    "classpath:/role.hierarchy",
                    "classpath:/role.hierarchy.txt",
                    "classpath:/META-INF/role.hierarchy",
                    "classpath:/META-INF/role.hierarchy.txt",
                    "classpath:/config/role.hierarchy",
                    "classpath:/config/role.hierarchy.txt"
            },
            existence = ConditionalOnResource.Existence.ANY,
            type = ConditionalOnResource.Type.FILE
    )
    RoleHierarchy roleHierarchy() {
        final RoleHierarchyImpl bean = new RoleHierarchyImpl();
        bean.setHierarchy(
                ResourceOptional.of(
                        "classpath:/role.hierarchy",
                        "classpath:/role.hierarchy.txt",
                        "classpath:/META-INF/role.hierarchy",
                        "classpath:/META-INF/role.hierarchy.txt",
                        "classpath:/config/role.hierarchy",
                        "classpath:/config/role.hierarchy.txt"
                ).toResourceText().getText()
        );
        return bean;
    }

}

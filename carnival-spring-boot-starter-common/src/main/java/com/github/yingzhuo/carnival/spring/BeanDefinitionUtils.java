/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * @author 应卓
 * @see SpringUtils
 * @since 1.3.6
 */
public final class BeanDefinitionUtils {

    private BeanDefinitionUtils() {
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static <B> void registerBean(String beanName, Class<B> beanClass, final B bean, String... alias) {
        SpringUtils.getBeanDefinitionRegistry().registerBeanDefinition(beanName,
                new RootBeanDefinition(beanClass, () -> bean));

        if (alias != null) {
            for (String a : alias) {
                SpringUtils.getBeanDefinitionRegistry().registerAlias(beanName, a);
            }
        }
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring.springid;

import java.util.Arrays;
import java.util.List;

/**
 * @author 应卓
 * @since 1.8.4
 */
public class CompositeSpringIdProvider implements SpringIdProvider {

    public static CompositeSpringIdProvider getInstance(SpringIdProvider... providers) {
        return new CompositeSpringIdProvider(Arrays.asList(providers));
    }

    private final List<SpringIdProvider> providers;

    public CompositeSpringIdProvider(List<SpringIdProvider> providers) {
        this.providers = providers;
    }

    @Override
    public String get() {
        if (providers == null || providers.isEmpty()) {
            return null;
        }

        for (SpringIdProvider provider : providers) {
            String id = provider.get();
            if (id != null) return id;
        }

        return null;
    }

}

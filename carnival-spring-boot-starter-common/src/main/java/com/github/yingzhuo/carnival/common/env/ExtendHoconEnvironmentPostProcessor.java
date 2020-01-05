/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.env;

import org.springframework.boot.env.HoconPropertySourceLoader;
import org.springframework.core.Ordered;

/**
 * @author 应卓
 * @since 1.4.1
 */
public class ExtendHoconEnvironmentPostProcessor extends AbstractEnvironmentPostProcessor implements Ordered {

    private static final String[] prefix = new String[]{
            "file:config/property-source",
            "file:property-source",
            "classpath:config/property-source",
            "classpath:property-source",
            "classpath:META-INF/property-source",
    };

    private static final String[] suffix = new String[]{".conf"};

    public ExtendHoconEnvironmentPostProcessor() {
        super(prefix, suffix, "carnival-extend", new HoconPropertySourceLoader());
    }

    @Override
    public int getOrder() {
        return 4;
    }

}

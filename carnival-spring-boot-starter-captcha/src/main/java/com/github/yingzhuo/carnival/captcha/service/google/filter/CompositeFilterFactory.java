/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.service.google.filter;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

/**
 * @author 应卓
 * @since 1.10.6
 */
public class CompositeFilterFactory implements FilterFactory {

    private final List<FilterFactory> filterFactories;

    public CompositeFilterFactory(FilterFactory... filterFactories) {
        this.filterFactories = Arrays.asList(filterFactories);
    }

    public CompositeFilterFactory(List<FilterFactory> filterFactories) {
        this.filterFactories = filterFactories;
    }

    public static CompositeFilterFactory of(FilterFactory... filterFactories) {
        return new CompositeFilterFactory(filterFactories);
    }

    public static CompositeFilterFactory of(List<FilterFactory> filterFactories) {
        return new CompositeFilterFactory(filterFactories);
    }

    @Override
    public BufferedImage apply(BufferedImage source) {
        BufferedImage image = source;
        for (FilterFactory factory : filterFactories) {
            image = factory.apply(image);
        }
        return image;
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.service.google.filter.predefined;

import com.github.yingzhuo.carnival.captcha.service.google.filter.AbstractFilterFactory;
import com.github.yingzhuo.carnival.captcha.service.google.filter.lib.RippleImageOp;

import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piotr Piastucki
 * @since 1.10.6
 */
abstract class AbstractRippleFilterFactory extends AbstractFilterFactory {

    protected List<BufferedImageOp> filters;
    protected RippleImageOp ripple;

    public AbstractRippleFilterFactory() {
        ripple = new RippleImageOp();
    }

    protected List<BufferedImageOp> getPreRippleFilters() {
        return new ArrayList<>();
    }

    protected List<BufferedImageOp> getPostRippleFilters() {
        return new ArrayList<>();
    }

    @Override
    public List<BufferedImageOp> getFilters() {
        if (filters == null) {
            filters = new ArrayList<>();
            filters.addAll(getPreRippleFilters());
            filters.add(ripple);
            filters.addAll(getPostRippleFilters());
        }
        return filters;
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package org.patchca.filter.predefined;

import org.patchca.filter.AbstractFilterFactory;
import org.patchca.filter.library.RippleImageOp;

import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piotr Piastucki
 */
public abstract class AbstractRippleFilterFactory extends AbstractFilterFactory {

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

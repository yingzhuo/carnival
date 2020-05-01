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
import org.patchca.filter.library.DoubleRippleImageOp;

import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piotr Piastucki
 */
public class DoubleRippleFilterFactory extends AbstractFilterFactory {

    protected List<BufferedImageOp> filters;
    protected DoubleRippleImageOp ripple;

    public DoubleRippleFilterFactory() {
        ripple = new DoubleRippleImageOp();
    }

    @Override
    public List<BufferedImageOp> getFilters() {
        if (filters == null) {
            filters = new ArrayList<>();
            filters.add(ripple);
        }

        return filters;
    }

}

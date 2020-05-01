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

import org.patchca.color.ColorFactory;
import org.patchca.filter.library.CurvesImageOp;

import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piotr Piastucki
 */
public class CurvesAbstractRippleFilterFactory extends AbstractRippleFilterFactory {

    protected CurvesImageOp curves = new CurvesImageOp();

    public CurvesAbstractRippleFilterFactory() {
        super();
    }

    public CurvesAbstractRippleFilterFactory(ColorFactory colorFactory) {
        setColorFactory(colorFactory);
    }

    @Override
    protected List<BufferedImageOp> getPreRippleFilters() {
        List<BufferedImageOp> list = new ArrayList<>();
        list.add(curves);
        return list;
    }

    public void setStrokeMin(float strokeMin) {
        curves.setStrokeMin(strokeMin);
    }

    public void setStrokeMax(float strokeMax) {
        curves.setStrokeMax(strokeMax);
    }

    public void setColorFactory(ColorFactory colorFactory) {
        curves.setColorFactory(colorFactory);
    }
}

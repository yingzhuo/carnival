/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.service.google.filter.lib;

/**
 * @author Piotr Piastucki
 * @since 1.10.6
 */
public abstract class AbstractTransformImageOp extends AbstractImageOp {

    private boolean initialized;

    public AbstractTransformImageOp() {
        setEdgeMode(EDGE_CLAMP);
    }

    protected abstract void transform(int x, int y, double[] t);

    protected void init() {
    }

    @Override
    protected void filter(int[] inPixels, int[] outPixels, int width, int height) {
        if (!initialized) {
            init();
            initialized = true;
        }
        double[] t = new double[2];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                transform(x, y, t);
                int pixel = getPixelBilinear(inPixels, t[0], t[1], width, height, getEdgeMode());
                outPixels[x + y * width] = pixel;
            }
        }
    }

}


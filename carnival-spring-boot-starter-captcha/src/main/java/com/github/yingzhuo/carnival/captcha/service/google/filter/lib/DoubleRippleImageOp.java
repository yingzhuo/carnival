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
public class DoubleRippleImageOp extends RippleImageOp {

    @Override
    protected void transform(int x, int y, double[] t) {
        double tx = Math.sin((double) y / yWavelength + yRandom) + 1.3 * Math.sin(0.6 * y / yWavelength + yRandom);
        double ty = Math.cos((double) x / xWavelength + xRandom) + 1.3 * Math.cos(0.6 * x / xWavelength + xRandom);
        t[0] = x + xAmplitude * tx;
        t[1] = y + yAmplitude * ty;
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.service.google.color;

import java.awt.*;

/**
 * @author Piotr Piastucki
 * @since 1.10.6
 */
public class GradientColorFactory implements ColorFactory {

    private Color start;
    private Color step;

    public GradientColorFactory() {
        start = new Color(192, 192, 0);
        step = new Color(192, 128, 128);
    }

    @Override
    public Color getColor(int index) {
        return new Color((start.getRed() + step.getRed() * index) % 256,
                (start.getGreen() + step.getGreen() * index) % 256,
                (start.getBlue() + step.getBlue() * index) % 256);
    }

    public void setStart(Color start) {
        this.start = start;
    }

    public void setStep(Color step) {
        this.step = step;
    }

}

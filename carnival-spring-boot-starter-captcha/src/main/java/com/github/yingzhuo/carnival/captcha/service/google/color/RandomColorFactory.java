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
import java.util.Random;

/**
 * @author Piotr Piastucki
 * @since 1.10.6
 */
public class RandomColorFactory implements ColorFactory {

    private Color min;
    private Color max;

    public RandomColorFactory() {
        min = new Color(0, 0, 0);
        max = new Color(255, 255, 255);
    }

    public void setMin(Color min) {
        this.min = min;
    }

    public void setMax(Color max) {
        this.max = max;
    }

    @Override
    public Color getColor(int index) {
        Random r = new Random();
        return new Color(min.getRed() + r.nextInt((max.getRed() - min.getRed())),
                min.getGreen() + r.nextInt((max.getGreen() - min.getGreen())),
                min.getBlue() + r.nextInt((max.getBlue() - min.getBlue())));
    }

}

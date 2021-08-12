/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.service.google.background;

import com.github.yingzhuo.carnival.captcha.service.google.color.ColorFactory;
import com.github.yingzhuo.carnival.captcha.service.google.color.SingleColorFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Piotr Piastucki
 * @since 1.10.6
 */
public class SingleColorBackgroundFactory implements BackgroundFactory {

    private ColorFactory colorFactory;

    public SingleColorBackgroundFactory() {
        colorFactory = new SingleColorFactory(Color.WHITE);
    }

    public SingleColorBackgroundFactory(Color color) {
        colorFactory = new SingleColorFactory(color);
    }

    public void setColorFactory(ColorFactory colorFactory) {
        this.colorFactory = colorFactory;
    }

    @Override
    public void fillBackground(BufferedImage dest) {
        Graphics g = dest.getGraphics();
        g.setColor(colorFactory.getColor(0));
        g.fillRect(0, 0, dest.getWidth(), dest.getHeight());
    }

}

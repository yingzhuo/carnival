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
public class SingleColorFactory implements ColorFactory {

    private Color color;

    public SingleColorFactory() {
        color = Color.BLACK;
    }

    public SingleColorFactory(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor(int index) {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}

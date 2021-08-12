/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.service.google.renderer;

/**
 * @author Piotr Piastucki
 * @since 1.10.6
 */
public class SimpleTextRenderer extends AbstractTextRenderer {

    @Override
    protected void arrangeCharacters(int width, int height, TextString ts) {
        double x = leftMargin;
        for (TextCharacter tc : ts.getCharacters()) {
            double y = topMargin + (height + tc.getAscent() * 0.7) / 2;
            tc.setX(x);
            tc.setY(y);
            x += tc.getWidth();
        }
    }

}

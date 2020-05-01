/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package org.patchca.text.renderer;

import java.util.Random;

/**
 * @author Piotr Piastucki
 */
public class RandomYBestFitTextRenderer extends AbstractTextRenderer {

    @Override
    protected void arrangeCharacters(int width, int height, TextString ts) {
        double widthRemaining = (width - ts.getWidth() - leftMargin - rightMargin) / ts.getCharacters().size();
        double vmiddle = height / 2;
        double x = leftMargin + widthRemaining / 2;
        Random r = new Random();
        height -= topMargin + bottomMargin;
        for (TextCharacter tc : ts.getCharacters()) {
            double heightRemaining = height - tc.getHeight();
            double y = vmiddle + 0.35 * tc.getAscent() + (1 - 2 * r.nextDouble()) * heightRemaining;
            tc.setX(x);
            tc.setY(y);
            x += tc.getWidth() + widthRemaining;
        }
    }

}

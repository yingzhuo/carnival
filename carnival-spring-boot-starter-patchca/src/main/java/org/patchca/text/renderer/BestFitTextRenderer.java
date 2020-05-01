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

/**
 * @author Piotr Piastucki
 */
public class BestFitTextRenderer extends AbstractTextRenderer {

    @Override
    protected void arrangeCharacters(int width, int height, TextString ts) {
        double widthRemaining = (width - ts.getWidth() - leftMargin - rightMargin) / ts.getCharacters().size();
        double x = leftMargin + widthRemaining / 2;
        height -= topMargin + bottomMargin;
        for (TextCharacter tc : ts.getCharacters()) {
            double y = topMargin + (height + tc.getAscent() * 0.7) / 2;
            tc.setX(x);
            tc.setY(y);
            x += tc.getWidth() + widthRemaining;
        }
    }
}

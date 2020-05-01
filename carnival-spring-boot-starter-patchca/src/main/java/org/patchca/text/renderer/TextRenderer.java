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

import org.patchca.color.ColorFactory;
import org.patchca.font.FontFactory;

import java.awt.image.BufferedImage;

/**
 * @author Piotr Piastucki
 */
public interface TextRenderer {

    void setLeftMargin(int leftMargin);

    void setRightMargin(int rightMargin);

    void setTopMargin(int topMargin);

    void setBottomMargin(int bottomMargin);

    void draw(String text, BufferedImage canvas, FontFactory fontFactory, ColorFactory colorFactory);

}

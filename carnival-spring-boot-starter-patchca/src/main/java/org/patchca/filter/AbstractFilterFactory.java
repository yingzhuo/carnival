/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package org.patchca.filter;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.List;

/**
 * @author Piotr Piastucki
 */
public abstract class AbstractFilterFactory implements FilterFactory {

    protected abstract List<BufferedImageOp> getFilters();

    public BufferedImage applyFilters(BufferedImage source) {
        BufferedImage dest = source;
        for (BufferedImageOp filter : getFilters()) {
            dest = filter.filter(dest, null);
        }
        int x = (source.getWidth() - dest.getWidth()) / 2;
        int y = (source.getHeight() - dest.getHeight()) / 2;
        source = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        source.getGraphics().drawImage(dest, x, y, null);
        return source;
    }

}

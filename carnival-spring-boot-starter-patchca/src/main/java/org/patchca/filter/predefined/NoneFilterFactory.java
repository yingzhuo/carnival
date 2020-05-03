/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package org.patchca.filter.predefined;

import org.patchca.filter.FilterFactory;

import java.awt.image.BufferedImage;

/**
 * @author 应卓
 * @since 1.6.3
 */
public class NoneFilterFactory implements FilterFactory {

    @Override
    public BufferedImage apply(BufferedImage source) {
        return source;
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package org.patchca.filter.library;

/**
 * @author Piotr Piastucki
 */
public class SoftenImageOp extends AbstractConvolveImageOp {

    private static final float[][] matrix = {{0 / 16f, 1 / 16f, 0 / 16f},
            {1 / 16f, 12 / 16f, 1 / 16f}, {0 / 16f, 1 / 16f, 0 / 16f}};

    public SoftenImageOp() {
        super(matrix);
    }
}

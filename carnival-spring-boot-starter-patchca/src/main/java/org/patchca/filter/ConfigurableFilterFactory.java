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

import java.awt.image.BufferedImageOp;
import java.util.List;

/**
 * @author Piotr Piastucki
 */
public class ConfigurableFilterFactory extends AbstractFilterFactory {

    private List<BufferedImageOp> filters;

    @Override
    public List<BufferedImageOp> getFilters() {
        return filters;
    }

    public void setFilters(List<BufferedImageOp> filters) {
        this.filters = filters;
    }

}

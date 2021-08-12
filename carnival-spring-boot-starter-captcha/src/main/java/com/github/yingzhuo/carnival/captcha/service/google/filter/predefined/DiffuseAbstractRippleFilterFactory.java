/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.service.google.filter.predefined;

import com.github.yingzhuo.carnival.captcha.service.google.filter.lib.DiffuseImageOp;

import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piotr Piastucki
 * @since 1.10.6
 */
public class DiffuseAbstractRippleFilterFactory extends AbstractRippleFilterFactory {

    protected DiffuseImageOp diffuse = new DiffuseImageOp();

    @Override
    protected List<BufferedImageOp> getPreRippleFilters() {
        List<BufferedImageOp> list = new ArrayList<>();
        list.add(diffuse);
        return list;
    }

}

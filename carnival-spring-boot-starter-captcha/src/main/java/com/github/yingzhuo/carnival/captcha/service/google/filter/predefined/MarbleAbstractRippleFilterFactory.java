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

import com.github.yingzhuo.carnival.captcha.service.google.filter.lib.MarbleImageOp;

import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piotr Piastucki
 * @since 1.10.6
 */
public class MarbleAbstractRippleFilterFactory extends AbstractRippleFilterFactory {

    protected MarbleImageOp marble = new MarbleImageOp();

    @Override
    protected List<BufferedImageOp> getPreRippleFilters() {
        List<BufferedImageOp> list = new ArrayList<>();
        list.add(marble);
        return list;
    }

}

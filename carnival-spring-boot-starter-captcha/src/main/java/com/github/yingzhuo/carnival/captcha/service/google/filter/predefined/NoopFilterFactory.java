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

import com.github.yingzhuo.carnival.captcha.service.google.filter.FilterFactory;

import java.awt.image.BufferedImage;

/**
 * @author 应卓
 * @since 1.10.6
 */
public class NoopFilterFactory implements FilterFactory {

    @Override
    public BufferedImage apply(BufferedImage source) {
        return source;
    }

}

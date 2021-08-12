/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.service.google.background;

import java.awt.image.BufferedImage;

/**
 * @author Piotr Piastucki
 * @author 应卓
 * @since 1.10.6
 */
@FunctionalInterface
public interface BackgroundFactory {

    public void fillBackground(BufferedImage dest);

}

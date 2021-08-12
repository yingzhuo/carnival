/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.service.google;

import com.github.yingzhuo.carnival.captcha.service.google.background.SingleColorBackgroundFactory;
import com.github.yingzhuo.carnival.captcha.service.google.color.SingleColorFactory;
import com.github.yingzhuo.carnival.captcha.service.google.filter.predefined.CurvesAbstractRippleFilterFactory;
import com.github.yingzhuo.carnival.captcha.service.google.font.RandomFontFactory;
import com.github.yingzhuo.carnival.captcha.service.google.renderer.BestFitTextRenderer;
import com.github.yingzhuo.carnival.captcha.service.google.word.AdaptiveRandomWordFactory;

/**
 * @author Piotr Piastucki
 * @since 1.10.6
 */
public class GoogleCaptchaService extends AbstractGoogleCaptchaService {

    public GoogleCaptchaService() {
        backgroundFactory = new SingleColorBackgroundFactory();
        wordFactory = new AdaptiveRandomWordFactory();
        fontFactory = new RandomFontFactory();
        textRenderer = new BestFitTextRenderer();
        colorFactory = new SingleColorFactory();
        filterFactory = new CurvesAbstractRippleFilterFactory(colorFactory);
        textRenderer.setLeftMargin(10);
        textRenderer.setRightMargin(10);
        width = 160;
        height = 70;
    }

}

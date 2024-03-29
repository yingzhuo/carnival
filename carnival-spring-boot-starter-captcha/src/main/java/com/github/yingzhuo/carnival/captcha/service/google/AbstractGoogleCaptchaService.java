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

import com.github.yingzhuo.carnival.captcha.AccessKeyGenerator;
import com.github.yingzhuo.carnival.captcha.Captcha;
import com.github.yingzhuo.carnival.captcha.CaptchaService;
import com.github.yingzhuo.carnival.captcha.service.google.background.BackgroundFactory;
import com.github.yingzhuo.carnival.captcha.service.google.color.ColorFactory;
import com.github.yingzhuo.carnival.captcha.service.google.filter.FilterFactory;
import com.github.yingzhuo.carnival.captcha.service.google.font.FontFactory;
import com.github.yingzhuo.carnival.captcha.service.google.renderer.TextRenderer;
import com.github.yingzhuo.carnival.captcha.service.google.word.WordFactory;

import java.awt.image.BufferedImage;

/**
 * @author Piotr Piastucki
 * @since 1.10.6
 */
public abstract class AbstractGoogleCaptchaService implements CaptchaService {

    protected FontFactory fontFactory;
    protected WordFactory wordFactory;
    protected ColorFactory colorFactory;
    protected BackgroundFactory backgroundFactory;
    protected TextRenderer textRenderer;
    protected FilterFactory filterFactory;
    protected int width;
    protected int height;
    protected AccessKeyGenerator accessKeyGenerator;

    public FontFactory getFontFactory() {
        return fontFactory;
    }

    public void setFontFactory(FontFactory fontFactory) {
        this.fontFactory = fontFactory;
    }

    public WordFactory getWordFactory() {
        return wordFactory;
    }

    public void setWordFactory(WordFactory wordFactory) {
        this.wordFactory = wordFactory;
    }

    public ColorFactory getColorFactory() {
        return colorFactory;
    }

    public void setColorFactory(ColorFactory colorFactory) {
        this.colorFactory = colorFactory;
    }

    public BackgroundFactory getBackgroundFactory() {
        return backgroundFactory;
    }

    public void setBackgroundFactory(BackgroundFactory backgroundFactory) {
        this.backgroundFactory = backgroundFactory;
    }

    public TextRenderer getTextRenderer() {
        return textRenderer;
    }

    public void setTextRenderer(TextRenderer textRenderer) {
        this.textRenderer = textRenderer;
    }

    public FilterFactory getFilterFactory() {
        return filterFactory;
    }

    public void setFilterFactory(FilterFactory filterFactory) {
        this.filterFactory = filterFactory;
    }

    public AccessKeyGenerator getAccessKeyGenerator() {
        return accessKeyGenerator;
    }

    public void setAccessKeyGenerator(AccessKeyGenerator accessKeyGenerator) {
        this.accessKeyGenerator = accessKeyGenerator;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public Captcha getCaptcha() {
        BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        backgroundFactory.fillBackground(bufImage);
        String word = wordFactory.getNextWord();
        textRenderer.draw(word, bufImage, fontFactory, colorFactory);
        bufImage = filterFactory.apply(bufImage);
        return new Captcha(accessKeyGenerator.generate(), word, bufImage);
    }

}

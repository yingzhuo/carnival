/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.service.google.font;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Piotr Piastucki
 * @since 1.10.6
 */
public class RandomFontFactory implements FontFactory {

    protected List<String> families;
    protected int minSize;
    protected int maxSize;
    protected boolean randomStyle;

    public RandomFontFactory() {
        families = new ArrayList<>();
        families.add("Verdana");
        families.add("Tahoma");
        minSize = 45;
        maxSize = 45;
    }

    public RandomFontFactory(List<String> families) {
        this();
        this.families = families;
    }

    public RandomFontFactory(String[] families) {
        this();
        this.families = Arrays.asList(families);
    }

    public RandomFontFactory(int size, List<String> families) {
        this(families);
        minSize = maxSize = size;
    }

    public RandomFontFactory(int size, String[] families) {
        this(families);
        minSize = maxSize = size;
    }

    public void setFamilies(List<String> families) {
        this.families = families;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public void setRandomStyle(boolean randomStyle) {
        this.randomStyle = randomStyle;
    }

    @Override
    public Font getFont(int index) {
        Random r = new Random();
        String family = families.get(r.nextInt(families.size()));
        boolean bold = r.nextBoolean() && randomStyle;
        int size = minSize;
        if (maxSize - minSize > 0) {
            size += r.nextInt(maxSize - minSize);
        }
        return new Font(family, bold ? Font.BOLD : Font.PLAIN, size);
    }

}

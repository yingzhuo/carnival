/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.model;

/**
 * @author 应卓
 */
public final class Color implements java.io.Serializable {

    public static final Color WHITE;
    public static final Color LIGHT_GRAY;
    public static final Color GRAY;
    public static final Color DARK_GRAY;
    public static final Color BLACK;
    public static final Color RED;
    public static final Color PINK;
    public static final Color ORANGE;
    public static final Color YELLOW;
    public static final Color GREEN;
    public static final Color MAGENTA;
    public static final Color CYAN;
    public static final Color BLUE;
    private static final long serialVersionUID = 5730688186059906304L;

    static {
        WHITE = new Color(255, 255, 255);
        LIGHT_GRAY = new Color(192, 192, 192);
        GRAY = new Color(128, 128, 128);
        DARK_GRAY = new Color(64, 64, 64);
        BLACK = new Color(0, 0, 0);
        RED = new Color(255, 0, 0);
        PINK = new Color(255, 175, 175);
        ORANGE = new Color(255, 200, 0);
        YELLOW = new Color(255, 255, 0);
        GREEN = new Color(0, 255, 0);
        MAGENTA = new Color(255, 0, 255);
        CYAN = new Color(0, 255, 255);
        BLUE = new Color(0, 0, 255);
    }

    private final int r;
    private final int g;
    private final int b;

    public Color(int r, int g, int b) {
        if (r < 0 || r > 255) {
            throw new IllegalArgumentException();
        }

        if (g < 0 || g > 255) {
            throw new IllegalArgumentException();
        }

        if (b < 0 || b > 255) {
            throw new IllegalArgumentException();
        }

        this.r = r;
        this.g = g;
        this.b = b;
    }

    public java.awt.Color toAwtColor() {
        return new java.awt.Color(r, g, b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;
        return r == color.r && g == color.g && b == color.b;
    }

    @Override
    public int hashCode() {
        int result = r;
        result = 31 * result + g;
        result = 31 * result + b;
        return result;
    }

    @Override
    public String toString() {
        return String.format("Color(%d, %d, %d)", r, g, b);
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }
}

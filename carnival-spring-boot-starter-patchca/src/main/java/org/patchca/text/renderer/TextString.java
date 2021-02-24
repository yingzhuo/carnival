/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package org.patchca.text.renderer;

import java.util.ArrayList;

/**
 * @author Piotr Piastucki
 */
public class TextString {

    private ArrayList<TextCharacter> characters = new ArrayList<TextCharacter>();

    public void clear() {
        characters.clear();
    }

    public void addCharacter(TextCharacter tc) {
        characters.add(tc);
    }

    public ArrayList<TextCharacter> getCharacters() {
        return characters;
    }

    public double getWidth() {
        double minx = 0;
        double maxx = 0;
        boolean first = true;
        for (TextCharacter tc : characters) {
            if (first) {
                minx = tc.getX();
                maxx = tc.getX() + tc.getWidth();
                first = false;
            } else {
                if (minx > tc.getX()) {
                    minx = tc.getX();
                }
                if (maxx < tc.getX() + tc.getWidth()) {
                    maxx = tc.getX() + tc.getWidth();
                }
            }

        }
        return maxx - minx;
    }

    public double getHeight() {
        double miny = 0;
        double maxy = 0;
        boolean first = true;
        for (TextCharacter tc : characters) {
            if (first) {
                miny = tc.getY();
                maxy = tc.getY() + tc.getHeight();
                first = false;
            } else {
                if (miny > tc.getY()) {
                    miny = tc.getY();
                }
                if (maxy < tc.getY() + tc.getHeight()) {
                    maxy = tc.getY() + tc.getHeight();
                }
            }

        }
        return maxy - miny;
    }

}

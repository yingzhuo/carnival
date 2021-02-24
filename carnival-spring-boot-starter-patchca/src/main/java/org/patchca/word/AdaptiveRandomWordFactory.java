/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package org.patchca.word;

import java.util.Random;

/**
 * @author Piotr Piastucki
 */
public class AdaptiveRandomWordFactory extends RandomWordFactory {

    protected String wideCharacters;

    public AdaptiveRandomWordFactory() {
        characters = "absdegkmnopwx23456789";
        wideCharacters = "mw";
    }

    public void setWideCharacters(String wideCharacters) {
        this.wideCharacters = wideCharacters;
    }

    @Override
    public String getNextWord() {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        StringBuilder chars = new StringBuilder(characters);
        int l = minLength + (maxLength > minLength ? rnd.nextInt(maxLength - minLength) : 0);
        for (int i = 0; i < l; i++) {
            int j = rnd.nextInt(chars.length());
            char c = chars.charAt(j);
            if (wideCharacters.indexOf(c) != -1) {
                for (int k = 0; k < wideCharacters.length(); k++) {
                    int idx = chars.indexOf(String.valueOf(wideCharacters.charAt(k)));
                    if (idx != -1) {
                        chars.deleteCharAt(idx);
                    }
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

}

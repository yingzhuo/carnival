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
public class RandomWordFactory implements WordFactory {

    protected String characters;
    protected int minLength;
    protected int maxLength;

    public RandomWordFactory() {
        characters = "absdegkmnopwx23456789";
        minLength = 6;
        maxLength = 6;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String getNextWord() {
        Random rnd = new Random();
        StringBuffer sb = new StringBuffer();
        int l = minLength + (maxLength > minLength ? rnd.nextInt(maxLength - minLength) : 0);
        for (int i = 0; i < l; i++) {
            int j = rnd.nextInt(characters.length());
            sb.append(characters.charAt(j));
        }
        return sb.toString();
    }

}

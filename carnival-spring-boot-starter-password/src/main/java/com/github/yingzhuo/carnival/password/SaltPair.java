/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * (immutable)
 *
 * @author 应卓
 * @since 1.1.12
 */
public final class SaltPair implements Serializable {

    public final String left;
    public final String right;

    public SaltPair(String left, String right) {
        this.left = left != null ? left : "";
        this.right = right != null ? right : "";
    }

    public static SaltPair of(String left, String right) {
        return new SaltPair(left, right);
    }

    public static SaltPair random(int length) {
        return new SaltPair(RandomStringUtils.randomAlphabetic(length), RandomStringUtils.randomAlphabetic(length));
    }

    public static SaltPair random(int leftLength, int rightLength) {
        return new SaltPair(RandomStringUtils.randomAlphabetic(leftLength), RandomStringUtils.randomAlphabetic(rightLength));
    }

    @Override
    public String toString() {
        return String.format("%s:%s", left, right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaltPair saltPair = (SaltPair) o;
        return Objects.equals(left, saltPair.left) &&
                Objects.equals(right, saltPair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

}

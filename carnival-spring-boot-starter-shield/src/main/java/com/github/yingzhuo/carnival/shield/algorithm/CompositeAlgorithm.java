/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.shield.algorithm;

import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * @author 应卓
 * @since 1.6.22
 */
public final class CompositeAlgorithm implements Algorithm {

    private final List<Algorithm> algorithms;

    public static Algorithm of(Algorithm... algorithms) {
        return new CompositeAlgorithm(Arrays.asList(algorithms));
    }

    private CompositeAlgorithm(List<Algorithm> algorithms) {
        Assert.noNullElements(algorithms, "algorithms must not be empty: it must contain at least 1 element");
        this.algorithms = algorithms;
    }

    @Override
    public String encrypt(String string) {
        String result = null;
        for (Algorithm algorithm : algorithms) {
            result = algorithm.encrypt(string);
        }
        return result;
    }

    @Override
    public String decrypt(String string) {
        String result = null;
        for (Algorithm algorithm : algorithms) {
            result = algorithm.decrypt(string);
        }
        return result;
    }

}

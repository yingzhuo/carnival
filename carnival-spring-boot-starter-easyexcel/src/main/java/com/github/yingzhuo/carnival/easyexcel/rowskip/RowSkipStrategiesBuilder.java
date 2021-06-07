/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.easyexcel.rowskip;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @since 1.9.2
 */
public final class RowSkipStrategiesBuilder {

    private final List<RowSkipStrategy> strategies = new ArrayList<>();

    private RowSkipStrategiesBuilder() {
    }

    public static RowSkipStrategiesBuilder newInstance() {
        return new RowSkipStrategiesBuilder();
    }

    public RowSkipStrategiesBuilder byRowNumber(int... rowNumbers) {
        Set<Integer> set = Arrays.stream(rowNumbers).map(n -> n - 1).boxed().collect(Collectors.toSet());
        this.strategies.add(new RowNumberSkipStrategy(set));
        return this;
    }

    public RowSkipStrategy build() {
        return new CompositeStrategy(Collections.unmodifiableList(strategies));
    }

}

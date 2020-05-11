/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.6.6
 */
public class CompositeExceptionTransformer implements ExceptionTransformer {

    private final List<ExceptionTransformer> transformers;

    public CompositeExceptionTransformer(List<ExceptionTransformer> transformers) {
        this.transformers = Collections.unmodifiableList(transformers);
    }

    public CompositeExceptionTransformer(ExceptionTransformer... transformers) {
        this.transformers = Collections.unmodifiableList(Arrays.asList(transformers));
    }

    @Override
    public Optional<Exception> transform(Exception from) {

        for (ExceptionTransformer transformer : transformers) {
            Optional<Exception> op = transformer.transform(from);
            if (op.isPresent()) {
                return op;
            }
        }

        return Optional.empty();
    }

    public List<ExceptionTransformer> getTransformers() {
        return transformers;
    }

}

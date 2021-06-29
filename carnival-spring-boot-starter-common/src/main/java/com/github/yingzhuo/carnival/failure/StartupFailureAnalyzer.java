/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.failure;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * @author 应卓
 * @see StartupFailure
 * @since 1.8.1
 */
public class StartupFailureAnalyzer extends AbstractFailureAnalyzer<StartupFailure> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, StartupFailure cause) {
        return new FailureAnalysis(
                cause.getDescription(),
                cause.getAction(),
                cause
        );
    }

}

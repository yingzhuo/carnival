/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow.parser;

import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.6.0
 */
public class CompositeStepTokenParser implements StepTokenParser, InitializingBean {

    private final List<StepTokenParser> parsers;

    public CompositeStepTokenParser(StepTokenParser... parsers) {
        this.parsers = Collections.unmodifiableList(Arrays.asList(parsers));
    }

    @Override
    public Optional<String> parse(NativeWebRequest request) {

        for (val parser : parsers) {
            val op = parser.parse(request);
            if (op.isPresent()) {
                return op;
            }
        }

        return Optional.empty();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (StepTokenParser parser : this.parsers) {
            if (parser instanceof InitializingBean) {
                ((InitializingBean) parser).afterPropertiesSet();
            }
        }
    }

    public List<StepTokenParser> getParsers() {
        return parsers;
    }

}

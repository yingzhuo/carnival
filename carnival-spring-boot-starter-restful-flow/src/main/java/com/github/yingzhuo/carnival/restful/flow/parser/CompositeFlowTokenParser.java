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
public class CompositeFlowTokenParser implements FlowTokenParser, InitializingBean {

    private final List<FlowTokenParser> parsers;

    public CompositeFlowTokenParser(List<FlowTokenParser> parsers) {
        this.parsers = Collections.unmodifiableList(parsers);
    }

    public CompositeFlowTokenParser(FlowTokenParser... parsers) {
        this.parsers = Collections.unmodifiableList(Arrays.asList(parsers));
    }

    public static CompositeFlowTokenParser of(FlowTokenParser... parsers) {
        return new CompositeFlowTokenParser(parsers);
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
        for (FlowTokenParser parser : this.parsers) {
            if (parser instanceof InitializingBean) {
                ((InitializingBean) parser).afterPropertiesSet();
            }
        }
    }

    public List<FlowTokenParser> getParsers() {
        return parsers;
    }

}

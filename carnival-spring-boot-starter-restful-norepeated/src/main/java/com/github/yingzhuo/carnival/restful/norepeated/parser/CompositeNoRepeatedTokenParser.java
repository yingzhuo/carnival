/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.norepeated.parser;

import lombok.val;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.6.7
 */
public class CompositeNoRepeatedTokenParser implements NoRepeatedTokenParser {

    private final List<NoRepeatedTokenParser> parsers;

    public CompositeNoRepeatedTokenParser(List<NoRepeatedTokenParser> parsers) {
        this.parsers = parsers;
    }

    public CompositeNoRepeatedTokenParser(NoRepeatedTokenParser... parsers) {
        this.parsers = Arrays.asList(parsers);
    }

    public static CompositeNoRepeatedTokenParser of(NoRepeatedTokenParser... parsers) {
        return new CompositeNoRepeatedTokenParser(parsers);
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

}

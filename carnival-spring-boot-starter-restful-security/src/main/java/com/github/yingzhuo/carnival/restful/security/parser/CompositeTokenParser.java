/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.parser;

import com.github.yingzhuo.carnival.restful.security.token.Token;
import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.4.8
 */
public class CompositeTokenParser implements TokenParser, InitializingBean {

    private int order = 0;
    private final List<TokenParser> parsers;

    public CompositeTokenParser(TokenParser... parsers) {
        this.parsers = Arrays.asList(parsers);
    }

    @Override
    public Optional<Token> parse(NativeWebRequest webRequest) {
        for (val parser : parsers) {
            val op = parser.parse(webRequest);
            if (op.isPresent()) {
                return op;
            }
        }
        return Optional.empty();
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (TokenParser parser : this.parsers) {
            if (parser instanceof InitializingBean) {
                ((InitializingBean) parser).afterPropertiesSet();
            }
        }
    }

}

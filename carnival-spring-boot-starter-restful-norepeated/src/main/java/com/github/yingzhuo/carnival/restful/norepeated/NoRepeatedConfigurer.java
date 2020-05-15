/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.norepeated;

import com.github.yingzhuo.carnival.exception.ExceptionTransformer;
import com.github.yingzhuo.carnival.restful.norepeated.parser.HttpParameterNoRepeatedTokenParser;
import com.github.yingzhuo.carnival.restful.norepeated.parser.NoRepeatedTokenParser;
import org.springframework.core.Ordered;

/**
 * @author 应卓
 * @since 1.6.7
 */
public interface NoRepeatedConfigurer {

    public default int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 800;
    }

    public default NoRepeatedTokenParser getNoRepeatedTokenParser() {
        return new HttpParameterNoRepeatedTokenParser("_norepeated_token");
    }

    public default ExceptionTransformer getExceptionTransformer() {
        return null;
    }

    public default String[] getPathPatterns() {
        return new String[]{"/", "/**"};
    }

}

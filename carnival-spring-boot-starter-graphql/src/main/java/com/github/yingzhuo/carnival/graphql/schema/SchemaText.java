/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.schema;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.10.14
 */
@FunctionalInterface
public interface SchemaText extends Serializable {

    public static SchemaTextBuilder builder() {
        return new SchemaTextBuilder();
    }

    public String getText();

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.core;

import graphql.schema.idl.RuntimeWiring;

/**
 * @author 应卓
 * @since 1.10.14
 */
@FunctionalInterface
public interface RuntimeWiringCustomizer {

    public RuntimeWiring.Builder customize(RuntimeWiring.Builder builder);

}

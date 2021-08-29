/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @since 1.10.14
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(EnableGraphQLImportSelector.class)
public @interface EnableGraphQL {

    public String url() default "graphql";

    public String[] sdl() default "classpath:schema.graphqls";

}

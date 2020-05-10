/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow;

import com.github.yingzhuo.carnival.restful.flow.autoconfig.RequestFlowCoreAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @since 1.6.6
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(RequestFlowCoreAutoConfig.class)
public @interface EnableRestfulFlow {
}

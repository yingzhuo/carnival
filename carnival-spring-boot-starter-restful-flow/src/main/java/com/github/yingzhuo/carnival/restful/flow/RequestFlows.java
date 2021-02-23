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

import com.github.yingzhuo.carnival.restful.flow.core.DefaultRequestFlowBean;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 * @since 1.3.6
 */
public final class RequestFlows {

    private RequestFlows() {
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static String newToken(String name, int step) {
        return SpringUtils.getBean(DefaultRequestFlowBean.class).newToken(name, step);
    }

}

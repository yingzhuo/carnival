/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow.core;

/**
 * @author 应卓
 * @since 1.6.1
 */
public final class RequestFlowContext {

    private static final ThreadLocal<String> NAME_HOLDER = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<Integer> STEP_HOLDER = ThreadLocal.withInitial(() -> null);

    public static String getName() {
        return NAME_HOLDER.get();
    }

    static void setName(String name) {
        NAME_HOLDER.set(name);
    }

    public static Integer getStep() {
        return STEP_HOLDER.get();
    }

    static void setStep(Integer prevStep) {
        STEP_HOLDER.set(prevStep);
    }

    static void clean() {
        NAME_HOLDER.remove();
        STEP_HOLDER.remove();
    }

    // -----------------------------------------------------------------------------------------------------------------

    private RequestFlowContext() {
    }

}

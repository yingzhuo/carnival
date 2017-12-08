/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.script;

import java.util.Hashtable;

/**
 * 脚本运行器
 *
 * @author 应卓
 */
@FunctionalInterface
@SuppressWarnings("unchecked")
public interface ScriptExecutor {

    public Object execute(String script, ScriptLanguage language, ScriptContext context, String resultEl);

    default public <R> R execute(String script, ScriptLanguage language, ScriptContext context, String resultEl, Class<R> resultType) {
        return (R) execute(script, language, context, resultEl);
    }

    default public <R> R execute(String script, ScriptLanguage language, ScriptContext context, Class<R> resultType) {
        return execute(script, language, context, null, resultType);
    }

    default public <R> R execute(String script, ScriptLanguage language, Class<R> resultType) {
        return execute(script, language, ScriptContext.empty(), null, resultType);
    }

    /**
     * Script语言类型
     */
    public enum ScriptLanguage {
        JAVA_SCRIPT // 暂时只支持JS
    }

    /**
     * Script运行上下文
     */
    public static final class ScriptContext extends Hashtable<String, Object> {

        private static final long serialVersionUID = -5100699299904917359L;

        public ScriptContext() {
            super(0);
        }

        public static ScriptContext empty() {
            return new ScriptContext();
        }
    }

    /**
     * Script运行期错误
     */
    public static final class ScriptRuntimeException extends RuntimeException {

        private static final long serialVersionUID = 5367328969769271453L;

        public ScriptRuntimeException(Throwable cause) {
            super(cause);
        }
    }

}

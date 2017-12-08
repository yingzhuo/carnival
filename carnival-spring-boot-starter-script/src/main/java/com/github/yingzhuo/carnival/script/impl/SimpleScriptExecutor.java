/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.script.impl;

import com.github.yingzhuo.carnival.script.ScriptExecutor;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Objects;

/**
 * @author 应卓
 */
public class SimpleScriptExecutor implements ScriptExecutor {

    private final static String JAVASCRIPT = "javascript";

    // 线程安全
    private final static ScriptEngine JS_SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName(JAVASCRIPT);

    @Override
    public Object execute(String script, ScriptLanguage language, ScriptContext context, String resultEl) {
        Objects.requireNonNull(language);

        if (context == null) {
            context = ScriptContext.empty();
        }

        Object result;
        context.forEach(JS_SCRIPT_ENGINE::put);
        try {
            result = JS_SCRIPT_ENGINE.eval(script);
            return resultEl != null ? JS_SCRIPT_ENGINE.get(resultEl) : result;
        } catch (ScriptException e) {
            throw new ScriptRuntimeException(e);
        }
    }
}

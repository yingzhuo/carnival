/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.io.ini;

/**
 * @author 应卓
 */
public class IniConfigException extends RuntimeException {

    private static final long serialVersionUID = -5438807559357721461L;

    public IniConfigException() {
        super();
    }

    public IniConfigException(String message) {
        super(message);
    }

}

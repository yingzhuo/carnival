/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.ssh2;

public interface ShellExecutor {

    /**
     * 调用脚本
     *
     * @param shellPath 脚本目录
     */
    public void execute(String shellPath);

    /**
     * 调用脚本
     *
     * @param shellPath 脚本目录
     * @param args      脚本参数
     */
    public void execute(String shellPath, String... args);

}

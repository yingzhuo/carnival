/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import org.springframework.boot.system.ApplicationHome;

import java.io.File;

/**
 * @author 应卓
 * @since 1.4.8
 */
public final class DeploymentUtils {

    public static File getDeploymentDir() {
        return new ApplicationHome().getDir();
    }

    public static File getDeploymentDir(Class<?> bootCls) {
        return new ApplicationHome(bootCls).getDir();
    }

    // -----------------------------------------------------------------------------------------------------------------

    private DeploymentUtils() {
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.config.util;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.4.15
 */
public final class JarDir implements Serializable {

    private final ApplicationHome home;

    private JarDir() {
        this.home = new ApplicationHome();
    }

    private JarDir(Class<?> sourceClass) {
        this.home = new ApplicationHome(sourceClass);
    }

    public static JarDir of() {
        return new JarDir();
    }

    public static JarDir of(Class<?> sourceClass) {
        return new JarDir(sourceClass);
    }

    public File getDir(String child) {
        if (child == null) {
            return this.home.getDir();
        }
        return new File(home.getDir(), child);
    }

    public Resource getDirAsResource(String child) {
        return new FileSystemResource(getDir(child));
    }

    public String getDirAsResourceLocation(String child) {
        return "file:" + getDir(child).getAbsolutePath();
    }

    @Override
    public String toString() {
        return this.home.getDir().getAbsolutePath();
    }

}

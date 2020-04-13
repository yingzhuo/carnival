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

/**
 * @author 应卓
 * @since 1.4.12
 */
public final class JarLocation {

    private final ApplicationHome home;

    public static JarLocation of() {
        return new JarLocation();
    }

    public static JarLocation of(Class<?> sourceClass) {
        return new JarLocation(sourceClass);
    }

    private JarLocation() {
        this.home = new ApplicationHome();
    }

    private JarLocation(Class<?> sourceClass) {
        this.home = new ApplicationHome(sourceClass);
    }

    public File getFile(String child) {
        return new File(home.getDir(), child);
    }

    public Resource getFileAsResource(String child) {
        return new FileSystemResource(getFile(child));
    }

    public String getFileAsResourceLocation(String child) {
        return "file:" + getFile(child).getAbsolutePath();
    }

}

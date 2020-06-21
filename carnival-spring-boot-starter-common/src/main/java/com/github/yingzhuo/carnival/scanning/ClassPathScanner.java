/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.scanning;

import org.springframework.beans.factory.config.BeanDefinition;

import java.util.Set;

/**
 * @author 应卓
 * @since 1.6.20
 */
public interface ClassPathScanner {

    /**
     * 创建扫描器
     */
    public static ClassPathScannerBuilder builder() {
        return new ClassPathScannerBuilder();
    }

    /**
     * 扫描
     *
     * @param basePackages 扫描起点
     * @return 扫描结果
     */
    public Set<BeanDefinition> scan(Iterable<String> basePackages);

}

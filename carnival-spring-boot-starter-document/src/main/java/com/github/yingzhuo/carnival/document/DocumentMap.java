/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.document;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author 应卓
 * @since 1.6.11
 */
public class DocumentMap implements Serializable {

    private final SortedMap<String, Resource> map = new TreeMap<>();
    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    public static DocumentMap newInstance() {
        return new DocumentMap();
    }

    private DocumentMap() {
    }

    public DocumentMap clear() {
        map.clear();
        return this;
    }

    public DocumentMap rootDoc(String location) {
        Objects.requireNonNull(location);
        map.put("", resourceLoader.getResource(location));
        return this;
    }

    public DocumentMap doc(String path, String location) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(location);

        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        map.put(path, resourceLoader.getResource(location));
        return this;
    }

    public SortedMap<String, Resource> getDocumentResources() {
        return Collections.unmodifiableSortedMap(map);
    }

}

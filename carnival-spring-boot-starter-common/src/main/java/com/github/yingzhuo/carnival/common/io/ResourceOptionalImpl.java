/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.io;

import com.github.yingzhuo.carnival.spring.ResourceUtils;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * @author 应卓
 * @since 1.7.1
 */
class ResourceOptionalImpl implements ResourceOptional {

    private Resource resource;
    private String location;

    public ResourceOptionalImpl(String... locations) {
        for (String location : locations) {
            Resource it = ResourceUtils.loadResource(location);
            if (it.exists() && it.isReadable()) {
                this.resource = it;
                this.location = location;
                return;
            }
        }
    }

    @Override
    public String getLocation() {
        if (isAbsent()) {
            throw new NoSuchElementException("ResourceOptional is absent");
        }
        return location;
    }

    @Override
    public Resource get() {
        if (isAbsent()) {
            throw new NoSuchElementException("ResourceOptional is absent");
        }
        return resource;
    }

    @Override
    public boolean isPresent() {
        return resource != null;
    }

    @Override
    public void close() throws IOException {
        if (isPresent()) {
            get().getInputStream().close();
        }
    }
}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.fastdfs.tracker")
public class TrackerProperties implements Serializable, InitializingBean {

    private Set<String> nodes = new HashSet<>();

    @Override
    public void afterPropertiesSet() {
        Assert.notEmpty(nodes, "tracker nodes not configured");
        nodes = nodes.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .collect(Collectors.toSet());

    }

}

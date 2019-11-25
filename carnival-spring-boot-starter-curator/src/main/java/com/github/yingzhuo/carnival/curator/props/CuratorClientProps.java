/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.curator.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.3.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.curator")
public class CuratorClientProps implements Serializable {

    private boolean enabled = true;
    private String connectString;
    private int sessionTimeoutMs = 60000;
    private int connectionTimeoutMs = 60000;
    private String namespace;
    private RetryProps retry = new RetryProps();

}

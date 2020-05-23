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

import java.io.Serializable;

/**
 * 表示文件Web服务器对象
 *
 * @author tobato
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.fastdfs.web")
public class WebProperties implements Serializable, InitializingBean {

    private String url;

    @Override
    public void afterPropertiesSet() {
        if (url != null) {
            if (url.endsWith("/")) {
                url = url + "/";
            }
        }
    }

}

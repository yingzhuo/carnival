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

/**
 * 表示文件Web服务器对象
 *
 * @author tobato
 * @author 应卓
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.fastdfs.web")
public class WebProperties implements InitializingBean {

    private String url = "";

    @Override
    public void afterPropertiesSet() {
        if (url != null && !url.isEmpty()) {
            if (!url.endsWith("/")) {
                url = url + "/";
            }
        }
    }

}

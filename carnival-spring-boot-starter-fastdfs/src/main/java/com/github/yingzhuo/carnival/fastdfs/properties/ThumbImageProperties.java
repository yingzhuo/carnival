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
 * 缩略图生成配置支持
 *
 * @author tobato
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.fastdfs.thumb-image")
public class ThumbImageProperties implements Serializable, InitializingBean {

    private int defaultWidth;
    private int defaultHeight;

    @Override
    public void afterPropertiesSet() {
    }

}

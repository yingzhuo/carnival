/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package org.patchca.filter.predefined;

import lombok.extern.slf4j.Slf4j;
import org.patchca.filter.FilterFactory;
import org.springframework.beans.factory.InitializingBean;

import java.awt.image.BufferedImage;

/**
 * @author 应卓
 * @since 1.6.3
 */
@Slf4j
public class NoneFilterFactory implements FilterFactory, InitializingBean {

    @Override
    public BufferedImage apply(BufferedImage source) {
        return source;
    }

    @Override
    public void afterPropertiesSet() {
        log.warn("~~~~~~");
        log.warn("DO NOT use {} in your production environment.", getClass().getName());
        log.warn("~~~~~~");
    }

}

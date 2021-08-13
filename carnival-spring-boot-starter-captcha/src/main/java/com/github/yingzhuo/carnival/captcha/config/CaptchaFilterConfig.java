/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.config;

import com.github.yingzhuo.carnival.captcha.core.CaptchaFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.Ordered;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.10.6
 */
@Getter
@Setter
public class CaptchaFilterConfig implements Serializable {

    private String[] urlPatterns = new String[]{"/captcha"};
    private String filterName = CaptchaFilter.class.getName();
    private int order = Ordered.LOWEST_PRECEDENCE;

}

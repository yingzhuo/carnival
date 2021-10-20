/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/**
 * @author 应卓
 * @since 1.10.31
 */
@ConditionalOnMissingBean(HttpFirewall.class)
class HttpFirewallAutoConfig {

    @Bean
    HttpFirewall httpFirewall() {
        StrictHttpFirewall bean = new StrictHttpFirewall();
        bean.setAllowSemicolon(true);
        return bean;
    }

}

/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.kubernetes.autoconfig;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.kubernetes.readiness-probe", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(KubernetesReadinessProbeAutoConfig.Props.class)
public class KubernetesReadinessProbeAutoConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<KubernetesReadinessProbeFilter> kubernetesReadinessProbeFilter(Props props) {
        val bean = new FilterRegistrationBean<KubernetesReadinessProbeFilter>(new KubernetesReadinessProbeFilter());
        bean.setUrlPatterns(props.getPaths());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.setName(KubernetesReadinessProbeFilter.class.getSimpleName());
        return bean;
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.kubernetes.readiness-probe")
    static class Props {
        private boolean enabled = true;
        private List<String> paths;

        public Props() {
            paths = new ArrayList<>();
            paths.add("/k8s-readiness");
            paths.add("/k8s/readiness");
            paths.add("/kubernetes/readiness");
            paths.add("/kubernetes-readiness");
        }
    }

    static class KubernetesReadinessProbeFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

}

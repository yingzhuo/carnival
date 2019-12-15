/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.meter.props;

import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @author 应卓
 * @since 1.3.5
 */
@ToString
public class MeterLabelConfig implements EnvironmentAware, InitializingBean {

    private Environment environment;

    private String project;
    private String application;
    private String layer;
    private String version;
    private String primaryProfile;

    public MeterLabelConfig(String project, String application, String layer, String version, String primaryProfile) {
        this.project = project;
        this.application = application;
        this.layer = layer;
        this.version = version;
        this.primaryProfile = primaryProfile;
    }

    public String getProject() {
        return this.project;
    }

    public String getApplication() {
        return this.application;
    }

    public String getLayer() {
        return this.layer;
    }

    public String getVersion() {
        return this.version;
    }

    public String getPrimaryProfile() {
        return this.primaryProfile;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void afterPropertiesSet() {
        // default application
        if (application == null || application.isEmpty()) {
            this.application = environment.getProperty("spring.application.name");
        }

        this.project = emptyToNull(this.project);
        this.application = emptyToNull(this.application);
        this.layer = emptyToNull(this.layer);
        this.version = emptyToNull(this.version);
    }

    private String emptyToNull(String s) {
        return s == null || s.isEmpty() ? null : s;
    }
}

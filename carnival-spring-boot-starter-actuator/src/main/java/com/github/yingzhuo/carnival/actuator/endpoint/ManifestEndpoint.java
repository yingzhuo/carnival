/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * @author 应卓
 * @since 1.6.5
 */
@Endpoint(id = "manifest")
public class ManifestEndpoint {

    private final Properties properties = new Properties();

    public ManifestEndpoint() {
        final Properties p = new Properties();

        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF");
            p.load(is);
        } catch (IOException e) {
            // NOP
        }

        p.remove("manifestFile");

        for (Object key : p.keySet()) {
            Object value = p.getProperty(key.toString());
            if (!Objects.equals("", value)) {
                properties.put(key, value);
            }
        }
    }

    @ReadOperation(produces = "application/json;charset=UTF-8")
    public Properties manifest() {
        return this.properties;
    }

}

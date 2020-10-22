/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.actuator.stateprobe;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.6.32
 */
@RestControllerEndpoint(id = "readiness")
public class ReadinessStateEndpoint extends AbstractStateEndpoint {

    @PostMapping("/{state}")
    public Map<String, Object> execute(@PathVariable("state") String state) {
        final ReadinessState s;

        try {
            if (enable(state)) {
                s = ReadinessState.ACCEPTING_TRAFFIC;
            } else {
                s = ReadinessState.REFUSING_TRAFFIC;
            }
        } catch (IllegalArgumentException exception) {
            return Collections.singletonMap("error", "unknown state '" + state + "'");
        }

        AvailabilityChangeEvent.publish(applicationContext, s);
        return Collections.singletonMap("current", s);
    }

}

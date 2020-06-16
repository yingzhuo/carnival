package com.github.yingzhuo.carnival.resilience4j.config;

import com.github.yingzhuo.carnival.resilience4j.util.FeignDecoratorsUtils;
import org.junit.Test;

public class HolderTestCases {

    @Test
    public void test1() {

        ConfigHolder holder = ConfigHolder.builder()
                .addFallback("hello", new Object(), NullPointerException.class)
                .build();

        FeignDecoratorsUtils.getDecorators("hello", holder);
    }

}

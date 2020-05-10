package com.github.yingzhuo.carnival.restful.flow.core;

@FunctionalInterface
public interface RequestFlowBean {

    public String newToken(String name, int step);

}

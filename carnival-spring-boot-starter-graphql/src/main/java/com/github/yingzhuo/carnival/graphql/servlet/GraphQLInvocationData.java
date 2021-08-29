/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.servlet;

import graphql.Assert;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.10.14
 */
public class GraphQLInvocationData implements Serializable {

    private final String query;
    private final String operationName;
    private final Map<String, Object> variables;

    public GraphQLInvocationData(String query, String operationName) {
        this(query, operationName, null);
    }

    public GraphQLInvocationData(String query, String operationName, Map<String, Object> variables) {
        this.query = Assert.assertNotNull(query, () -> "query must be provided");
        this.operationName = operationName;
        this.variables = variables != null ? variables : Collections.emptyMap();
    }

    public String getQuery() {
        return query;
    }

    public String getOperationName() {
        return operationName;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

}

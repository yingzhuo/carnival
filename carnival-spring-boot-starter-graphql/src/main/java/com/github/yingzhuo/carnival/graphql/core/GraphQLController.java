/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.core;

import com.github.yingzhuo.carnival.graphql.request.Variables;
import com.github.yingzhuo.carnival.graphql.servlet.*;
import graphql.ExecutionResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * GraphQL基于HTTP，所有请求入口
 *
 * @author 应卓
 */
@RestController
public class GraphQLController {

    private final static String APPLICATION_GRAPHQL_VALUE = "application/graphql";
    private final static MediaType APPLICATION_GRAPHQL = MediaType.parseMediaType(APPLICATION_GRAPHQL_VALUE);

    private final GraphQLInvocation graphQLInvocation;
    private final ExecutionResultHandler executionResultHandler;
    private final JsonSerializer jsonSerializer;

    public GraphQLController(GraphQLInvocation graphQLInvocation, ExecutionResultHandler executionResultHandler, JsonSerializer jsonSerializer) {
        this.graphQLInvocation = graphQLInvocation;
        this.executionResultHandler = executionResultHandler;
        this.jsonSerializer = jsonSerializer;
    }

    @RequestMapping(
            value = "${graphql.url:graphql}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Object graphqlPOST(
            @RequestHeader(value = HttpHeaders.CONTENT_TYPE, required = false) String contentType,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "operationName", required = false) String operationName,
            @RequestParam(value = "variables", required = false) String variablesJson,
            @RequestBody(required = false) String body,
            WebRequest webRequest,
            InvokeContext ignored) {

        MediaType mediaType = null;
        if (StringUtils.hasLength(contentType)) {
            try {
                mediaType = MediaType.parseMediaType(contentType);
            } catch (InvalidMediaTypeException ignore) {
            }
        }

        if (body == null) {
            body = "";
        }

        // https://graphql.org/learn/serving-over-http/#post-request
        //
        // A standard GraphQL POST request should use the application/json content type,
        // and include a JSON-encoded body of the following form:
        //
        // {
        //   "query": "...",
        //   "operationName": "...",
        //   "variables": { "myVariable": "someValue", ... }
        // }

        if (MediaType.APPLICATION_JSON.equalsTypeAndSubtype(mediaType)) {
            GraphQLRequestBody request = jsonSerializer.deserialize(body, GraphQLRequestBody.class);
            if (request.getQuery() == null) {
                request.setQuery("");
            }
            return executeRequest(request.getQuery(), request.getOperationName(), request.getVariables(), webRequest);
        }

        // In addition to the above, we recommend supporting two additional cases:
        //
        // * If the "query" query string parameter is present (as in the GET example above),
        //   it should be parsed and handled in the same way as the HTTP GET case.

        if (query != null) {
            return executeRequest(query, operationName, convertVariablesJson(variablesJson), webRequest);
        }

        // * If the "application/graphql" Content-Type header is present,
        //   treat the HTTP POST body contents as the GraphQL query string.

        if (APPLICATION_GRAPHQL.equalsTypeAndSubtype(mediaType)) {
            return executeRequest(body, null, null, webRequest);
        }

        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Could not process GraphQL request");
    }

    @RequestMapping(
            value = "${graphql.url:graphql}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Object graphqlGET(
            @RequestParam("query") String query,
            @RequestParam(value = "operationName", required = false) String operationName,
            @RequestParam(value = "variables", required = false) String variablesJson,
            WebRequest webRequest,
            InvokeContext ignored) {

        // https://graphql.org/learn/serving-over-http/#get-request
        //
        // When receiving an HTTP GET request, the GraphQL query should be specified in the "query" query string.
        // For example, if we wanted to execute the following GraphQL query:
        //
        // {
        //   me {
        //     name
        //   }
        // }
        //
        // This request could be sent via an HTTP GET like so:
        //
        // http://myapi/graphql?query={me{name}}
        //
        // Query variables can be sent as a JSON-encoded string in an additional query parameter called "variables".
        // If the query contains several named operations,
        // an "operationName" query parameter can be used to control which one should be executed.

        return executeRequest(query, operationName, convertVariablesJson(variablesJson), webRequest);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> convertVariablesJson(String jsonMap) {
        if (jsonMap == null) {
            return Collections.emptyMap();
        }
        return jsonSerializer.deserialize(jsonMap, Map.class);
    }

    private Object executeRequest(
            String query,
            String operationName,
            Map<String, Object> variables,
            WebRequest webRequest) {

        try {
            InvokeContext.OperationNameHolder.set(operationName);
            InvokeContext.VariablesHolder.set(Variables.fromMap(variables));

            GraphQLInvocationData invocationData = new GraphQLInvocationData(query, operationName, variables);
            CompletableFuture<ExecutionResult> executionResult = graphQLInvocation.invoke(invocationData, webRequest);
            return executionResultHandler.handleExecutionResult(executionResult);
        } finally {
            InvokeContext.cleanup();
        }

    }

}

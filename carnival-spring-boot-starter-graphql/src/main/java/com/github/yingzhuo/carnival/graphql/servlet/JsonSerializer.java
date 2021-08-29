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

public interface JsonSerializer {

    /**
     * Serializes the given object to a json {@link String}.
     *
     * @param object the object to serialize
     * @return the json string
     */
    public String serialize(Object object);

    /**
     * Deserializes the given json {@link String} to an object of the required type.
     *
     * @param json         the json string
     * @param requiredType the required type
     * @param <T>          the required generic type
     * @return the object
     */
    public <T> T deserialize(String json, Class<T> requiredType);

}

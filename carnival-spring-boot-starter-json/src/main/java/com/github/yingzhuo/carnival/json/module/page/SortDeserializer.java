/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.json.module.page;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.data.domain.Sort;

/**
 * @author 应卓
 * @since 1.7.0
 */
class SortDeserializer extends JsonDeserializer<Sort> {

    @Override
    public Sort deserialize(JsonParser jp, DeserializationContext ctxt) {
//        ArrayNode node = jp.getCodec().readTree(jp);
//        Order[] orders = new Order[node.size()];
//        int i = 0;
//        for (JsonNode obj : node) {
//            orders[i] = new Order(Direction.valueOf(obj.get("direction").asText()), obj.get("property").asText());
//            i++;
//        }
//        return Sort.by(orders);
        return null;
    }

}

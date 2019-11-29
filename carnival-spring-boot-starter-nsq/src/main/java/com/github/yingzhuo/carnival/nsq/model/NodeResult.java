/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.3.1
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NodeResult {

    @JsonProperty("producers")
    private List<Producer> producers = new ArrayList<>();

}

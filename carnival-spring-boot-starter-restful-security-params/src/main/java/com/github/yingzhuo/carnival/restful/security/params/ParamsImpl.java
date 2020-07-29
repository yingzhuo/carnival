/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.params;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 应卓
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class ParamsImpl implements Params {
    private final String nonce;
    private final Long timestamp;
    private final String sign;

    public static void main(String[] args) {
        System.out.println(
                new ParamsImpl("123456", 1L, "123")
        );
    }

}

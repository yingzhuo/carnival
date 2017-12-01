/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.integration.smsbao;

public interface SmsbaoManager {

    /**
     * 发送短消息
     *
     * @param phoneNumber 电话号码
     * @param content     短消息内容
     * @return 成功与否标记
     */
    boolean send(String phoneNumber, String content);

    /**
     * 发送短消息
     *
     * @param phoneNumbers 电话号码 (多个)
     * @param content      短消息内容
     * @return 成功与否标记
     */
    default public boolean send(Iterable<String> phoneNumbers, String content) {
        StringBuilder builder = new StringBuilder();
        for (String phoneNumber : phoneNumbers) {
            builder.append(phoneNumber);
            builder.append(",");
        }
        String phoneNumber = builder.toString();
        if (phoneNumber.endsWith(",")) {
            phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
        }
        return send(phoneNumber, content);
    }

}

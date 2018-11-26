package com.github.yingzhuo.carnival.wechatpay.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class SignatureHelper {

    public enum DigestType {
        MD5,

        SHA1
    }

    public enum CharacterCase {
        /*大写*/
        UPPER_CASE,

        /*小写*/
        LOWER_CASE
    }

    private static SignatureHelper instance;

    public static SignatureHelper getInstance() {
        if (instance == null) {
            instance = new SignatureHelper();
        }
        return instance;
    }

    public String getSign2(Map<String, String> params, String keyName, String keyValue, boolean encode,
                           DigestType digestType, CharacterCase characterCase) {
        Set<String> keysSet = params.keySet();
        List<String> keyList = new ArrayList<>(keysSet);
        keyList.sort(StringUtils::compareIgnoreCase);

        StringBuilder buf = new StringBuilder();
        for (String key : keyList) {
            buf.append("&").append(key).append("=");
            String value = params.get(key);
            if (encode) {
                try {
                    buf.append(URLEncoder.encode(value, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    log.error("key = {}, value = {}", key, value, e);
                }
            } else {
                buf.append(value);
            }
        }

        if (StringUtils.isNotEmpty(keyName)) {
            buf.append("&").append(keyName).append("=");
        }
        buf.append(keyValue);
//		logger.info("签名字段:{} 全部是:{}",buf.substring(1),buf.toString());
        String sign;
        if (digestType == DigestType.SHA1) {

            sign = DigestUtils.sha1Hex(buf.substring(1));
        } else {
            sign = DigestUtils.md5Hex(buf.substring(1));
        }
        if (characterCase == CharacterCase.UPPER_CASE) {
            sign = sign.toUpperCase();
        } else {
            sign = sign.toLowerCase();
        }
        log.info("sign = {}", sign);
        return sign;
    }
}

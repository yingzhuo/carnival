/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.alipay.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.github.yingzhuo.carnival.alipay.AlipayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.text.DecimalFormat;

/**
 * @author 应卓
 */
@Slf4j
public class DefaultAlipayService implements AlipayService, InitializingBean {

    private final DecimalFormat decimalFormat = new DecimalFormat("######0.00");

    private static final String URL = "https://openapi.alipay.com/gateway.do";
    private static final String CHARSET = "UTF-8";
    private static final String SIGN_TYPE = "RSA2";
    private static final String PACKAGE_FORMAT = "json";

    private String appId;
    private String privateKey;
    private String publicKey;
    private String notifyUrl;
    private String productCode;
    private boolean debugMode;

    @Override
    public String createPaymentKey(String tradeNo, int amountOfMoney, String subject, String expireTime, String passbackParams) {

        final AlipayClient client = new DefaultAlipayClient(URL, this.appId, privateKey, PACKAGE_FORMAT, CHARSET, publicKey, SIGN_TYPE);
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        if (passbackParams != null) {
            model.setPassbackParams(passbackParams);
        }

        model.setSubject(subject);
        model.setOutTradeNo(tradeNo);

        if (expireTime != null) {
            model.setTimeExpire(expireTime);
        } else {
            model.setTimeoutExpress("2h");//最长2小时
        }

        model.setTotalAmount(converterAmountOfMoney(amountOfMoney));
        model.setProductCode(productCode);  //销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY

        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);

        try {
            AlipayTradeAppPayResponse response = client.sdkExecute(request);
            log.info("查看目前支付宝返回体情况:{}", response.getBody());
            return response.getBody();
        } catch (AlipayApiException e) {
            log.error(e.getMessage(), e);
            throw new IllegalStateException(e.getMessage(), e);
        }
    }


    @Override
    public boolean isTradeSuccess(String tradeNo) {
        if (this.debugMode) {
            return true;
        }

        final AlipayClient client = new DefaultAlipayClient(URL, appId, privateKey, PACKAGE_FORMAT, CHARSET, publicKey, SIGN_TYPE);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setOutTradeNo(tradeNo);
        request.setBizModel(model);
        try {
            AlipayTradeQueryResponse response = client.execute(request);
            return StringUtils.equalsIgnoreCase("TRADE_SUCCESS", response.getTradeStatus());
        } catch (AlipayApiException e) {
            log.error(e.getMessage(), e);
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * 支付金额转换成文本
     *
     * @param amountOfMoney 支付金额(分)
     * @return 转换后的文本
     */
    private String converterAmountOfMoney(int amountOfMoney) {
        if (debugMode) {
            return "0.01";
        }

        double payment = (double) amountOfMoney;
        payment = payment / 100.00;
        return decimalFormat.format(payment);
    }

    @Override
    public void afterPropertiesSet() {
        Assert.hasText(appId, "appId is blank or null.");
        Assert.hasText(privateKey, "privateKey is blank or null.");
        Assert.hasText(publicKey, "publicKey is blank or null.");
        Assert.hasText(notifyUrl, "notifyUrl is blank or null.");
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}

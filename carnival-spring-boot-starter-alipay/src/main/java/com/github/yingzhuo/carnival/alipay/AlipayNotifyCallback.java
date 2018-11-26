/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.alipay;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author 应卓
 */
@Deprecated
public interface AlipayNotifyCallback {

    public void execute(CallbackParameters params);

    public static class NopAlipayNotifyCallback implements AlipayNotifyCallback {
        @Override
        public void execute(CallbackParameters params) {
            // NOP
        }
    }

    public static class CallbackParameters {

        private final String notifyTime;
        private final String notifyType;
        private final String notifyId;
        private final String appId;
        private final String charset;
        private final String version;
        private final String signType;
        private final String sign;
        private final String tradeNo;
        private final String outTradeNo;
        private final String outBizNo;
        private final String buyerId;
        private final String buyerLogonId;
        private final String sellerId;
        private final String sellerEmail;
        private final String tradeStatus;
        private final String totalAmount;
        private final String receiptAmount;
        private final String invoiceAmount;
        private final String buyerPayAmount;
        private final String pointAmount;
        private final String refundFee;
        private final String subject;
        private final String body;
        private final String gmtCreate;
        private final String gmtPayment;
        private final String gmtRefund;
        private final String gmtClose;
        private final String fundBillList;
        private final String passbackParams;
        private final String voucherDetailList;

        public CallbackParameters(HttpServletRequest request) {
            Objects.requireNonNull(request);
            this.notifyTime = request.getParameter("notify_time");
            this.notifyType = request.getParameter("notify_type");
            this.notifyId = request.getParameter("notify_id");
            this.appId = request.getParameter("app_id");
            this.charset = request.getParameter("charset");
            this.version = request.getParameter("version");
            this.signType = request.getParameter("sign_type");
            this.sign = request.getParameter("sign");
            this.tradeNo = request.getParameter("trade_no");
            this.outTradeNo = request.getParameter("out_trade_no");
            this.outBizNo = request.getParameter("out_biz_no");
            this.buyerId = request.getParameter("buyer_id");
            this.buyerLogonId = request.getParameter("buyer_logon_id");
            this.sellerId = request.getParameter("seller_id");
            this.sellerEmail = request.getParameter("seller_email");
            this.tradeStatus = request.getParameter("trade_status");
            this.totalAmount = request.getParameter("total_amount");
            this.receiptAmount = request.getParameter("receipt_amount");
            this.invoiceAmount = request.getParameter("invoice_amount");
            this.buyerPayAmount = request.getParameter("buyer_pay_amount");
            this.pointAmount = request.getParameter("point_amount");
            this.refundFee = request.getParameter("refund_fee");
            this.subject = request.getParameter("subject");
            this.body = request.getParameter("body");
            this.gmtCreate = request.getParameter("gmt_create");
            this.gmtPayment = request.getParameter("gmt_payment");
            this.gmtRefund = request.getParameter("gmt_refund");
            this.gmtClose = request.getParameter("gmt_close");
            this.fundBillList = request.getParameter("fund_bill_list");
            this.passbackParams = request.getParameter("passback_params");
            this.voucherDetailList = request.getParameter("voucher_detail_list");
        }

        public String getNotifyTime() {
            return notifyTime;
        }

        public String getNotifyType() {
            return notifyType;
        }

        public String getNotifyId() {
            return notifyId;
        }

        public String getAppId() {
            return appId;
        }

        public String getCharset() {
            return charset;
        }

        public String getVersion() {
            return version;
        }

        public String getSignType() {
            return signType;
        }

        public String getSign() {
            return sign;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public String getOutBizNo() {
            return outBizNo;
        }

        public String getBuyerId() {
            return buyerId;
        }

        public String getBuyerLogonId() {
            return buyerLogonId;
        }

        public String getSellerId() {
            return sellerId;
        }

        public String getSellerEmail() {
            return sellerEmail;
        }

        public String getTradeStatus() {
            return tradeStatus;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public String getReceiptAmount() {
            return receiptAmount;
        }

        public String getInvoiceAmount() {
            return invoiceAmount;
        }

        public String getBuyerPayAmount() {
            return buyerPayAmount;
        }

        public String getPointAmount() {
            return pointAmount;
        }

        public String getRefundFee() {
            return refundFee;
        }

        public String getSubject() {
            return subject;
        }

        public String getBody() {
            return body;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public String getGmtPayment() {
            return gmtPayment;
        }

        public String getGmtRefund() {
            return gmtRefund;
        }

        public String getGmtClose() {
            return gmtClose;
        }

        public String getFundBillList() {
            return fundBillList;
        }

        public String getPassbackParams() {
            return passbackParams;
        }

        public String getVoucherDetailList() {
            return voucherDetailList;
        }
    }

}

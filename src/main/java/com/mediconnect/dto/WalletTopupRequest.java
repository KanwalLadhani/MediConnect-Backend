package com.mediconnect.dto;

public class WalletTopupRequest {
    private Long workerId;
    private Double amountPkr;
    private String method; // "JAZZCASH", "EASYPAISA", "BANK_TRANSFER"
    private String transactionReference;

    public WalletTopupRequest() {}

    public Long getWorkerId() { return workerId; }
    public void setWorkerId(Long workerId) { this.workerId = workerId; }

    public Double getAmountPkr() { return amountPkr; }
    public void setAmountPkr(Double amountPkr) { this.amountPkr = amountPkr; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }
}

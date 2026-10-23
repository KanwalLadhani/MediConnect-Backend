package com.mediconnect.dto;

public class ChatMessageRequest {
    private Long orderId;
    private String senderRole; // "PATIENT", "WORKER"
    private String senderName;
    private String message;

    public ChatMessageRequest() {}

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getSenderRole() { return senderRole; }
    public void setSenderRole(String senderRole) { this.senderRole = senderRole; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

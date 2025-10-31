package com.ldr.api.dto;

import com.ldr.api.model.OrderAttachment;

public class FileUploadResponse {

    private boolean success;
    private String message;
    private OrderAttachment attachment;
    private String fileUrl;

    public FileUploadResponse() {
    }

    public FileUploadResponse(boolean success, String message, OrderAttachment attachment, String fileUrl) {
        this.success = success;
        this.message = message;
        this.attachment = attachment;
        this.fileUrl = fileUrl;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrderAttachment getAttachment() {
        return attachment;
    }

    public void setAttachment(OrderAttachment attachment) {
        this.attachment = attachment;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
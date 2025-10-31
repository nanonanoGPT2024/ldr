package com.ldr.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderActivityResponse {

    private List<OrderAttachmentActivity> attachments;

    public OrderActivityResponse() {
    }

    public OrderActivityResponse(List<OrderAttachmentActivity> attachments) {
        this.attachments = attachments;
    }

    public List<OrderAttachmentActivity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<OrderAttachmentActivity> attachments) {
        this.attachments = attachments;
    }

    public static class OrderAttachmentActivity {
        private String documentName;
        private LocalDateTime createdAt;
        private String keterangan;

        public OrderAttachmentActivity() {
        }

        public OrderAttachmentActivity(String documentName, LocalDateTime createdAt, String keterangan) {
            this.documentName = documentName;
            this.createdAt = createdAt;
            this.keterangan = keterangan;
        }

        public String getDocumentName() {
            return documentName;
        }

        public void setDocumentName(String documentName) {
            this.documentName = documentName;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }
    }
}
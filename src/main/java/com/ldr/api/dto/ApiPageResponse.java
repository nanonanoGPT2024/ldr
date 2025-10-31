package com.ldr.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Global response wrapper untuk pagination API responses
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiPageResponse<T> {

    private boolean success;
    private String message;
    private List<T> content;
    private PageMetadata page;

    // Constructors
    public ApiPageResponse() {}

    public ApiPageResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiPageResponse(boolean success, String message, Page<T> pageData) {
        this.success = success;
        this.message = message;
        this.content = pageData.getContent();
        this.page = new PageMetadata(pageData);
    }

    // Inner class untuk page metadata
    public static class PageMetadata {
        private int page;
        private long totalElements;
        private int totalPages;

        public PageMetadata(Page<?> pageData) {
            this.page = pageData.getNumber();
            this.totalElements = pageData.getTotalElements();
            this.totalPages = pageData.getTotalPages();
        }

        // Getters and Setters
        public int getPage() { return page; }
        public void setPage(int page) { this.page = page; }

        public long getTotalElements() { return totalElements; }
        public void setTotalElements(long totalElements) { this.totalElements = totalElements; }

        public int getTotalPages() { return totalPages; }
        public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<T> getContent() { return content; }
    public void setContent(List<T> content) { this.content = content; }

    public PageMetadata getPage() { return page; }
    public void setPage(PageMetadata page) { this.page = page; }
}
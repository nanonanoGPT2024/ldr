/*
Navicat Premium Data Transfer

Source Server         : mysql lokal
Source Server Type    : MySQL
Source Server Version : 80403 (8.4.3)
Source Host           : localhost:3306
Source Schema         : ldr

Target Server Type    : MySQL
Target Server Version : 80403 (8.4.3)
File Encoding         : 65001

Date: 31/10/2025 08:10:06
*/

SET NAMES utf8mb4;

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_approval
-- ----------------------------
DROP TABLE IF EXISTS `order_approval`;

CREATE TABLE `order_approval` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `order_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `approver_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `approver_role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `status` enum(
        'REQUEST',
        'APPROVED',
        'REJECTED',
        'RETURN'
    ) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'REQUEST',
    `comments` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `approved_at` timestamp NULL DEFAULT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_order_approval_order_id` (`order_id` ASC) USING BTREE,
    INDEX `idx_order_approval_approver_id` (`approver_id` ASC) USING BTREE,
    INDEX `idx_order_approval_status` (`status` ASC) USING BTREE,
    CONSTRAINT `order_approval_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_data` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_approval_ibfk_2` FOREIGN KEY (`approver_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Tracking approval untuk setiap order' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_approval
-- ----------------------------

-- ----------------------------
-- Table structure for order_assignment_history
-- ----------------------------
DROP TABLE IF EXISTS `order_assignment_history`;

CREATE TABLE `order_assignment_history` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `order_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `assigned_to` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `assigned_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `assignment_type` enum(
        'ASSIGNED',
        'REASSIGNED',
        'UNASSIGNED'
    ) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `notes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `assigned_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `assigned_by` (`assigned_by` ASC) USING BTREE,
    INDEX `idx_order_assignment_history_order_id` (`order_id` ASC) USING BTREE,
    INDEX `idx_order_assignment_history_assigned_to` (`assigned_to` ASC) USING BTREE,
    INDEX `idx_order_assignment_history_assigned_at` (`assigned_at` ASC) USING BTREE,
    INDEX `idx_order_assignment_history_order_assigned` (
        `order_id` ASC,
        `assigned_at` ASC
    ) USING BTREE,
    CONSTRAINT `order_assignment_history_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_data` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_assignment_history_ibfk_2` FOREIGN KEY (`assigned_to`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_assignment_history_ibfk_3` FOREIGN KEY (`assigned_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'History perubahan assignment untuk setiap order' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_assignment_history
-- ----------------------------

-- ----------------------------
-- Table structure for order_attachment
-- ----------------------------
DROP TABLE IF EXISTS `order_attachment`;

CREATE TABLE `order_attachment` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `order_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `document_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `keterangan` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `original_file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Original file name before upload',
    `file_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `file_size` bigint NULL DEFAULT NULL,
    `file_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `mime_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `uploaded_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    `is_active` tinyint(1) NULL DEFAULT 1,
    `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT 'Soft delete',
    `deleted_at` timestamp NULL DEFAULT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_order_attachment_order_id` (`order_id` ASC) USING BTREE,
    INDEX `idx_order_attachment_document_id` (`document_id` ASC) USING BTREE,
    INDEX `idx_order_attachment_uploaded_by` (`uploaded_by` ASC) USING BTREE,
    INDEX `idx_order_attachment_created_at` (`created_at` ASC) USING BTREE,
    INDEX `idx_order_attachment_is_active` (`is_active` ASC) USING BTREE,
    INDEX `idx_order_attachment_is_deleted` (`is_deleted` ASC) USING BTREE,
    INDEX `idx_order_attachment_order_document` (
        `order_id` ASC,
        `document_id` ASC
    ) USING BTREE,
    CONSTRAINT `order_attachment_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_data` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_attachment_ibfk_2` FOREIGN KEY (`document_id`) REFERENCES `rf_document` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_attachment_ibfk_3` FOREIGN KEY (`uploaded_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Management attachment untuk order dengan versioning dan soft delete' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for order_attachment_history
-- ----------------------------
DROP TABLE IF EXISTS `order_attachment_history`;

CREATE TABLE `order_attachment_history` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `order_attachment_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'FK to order_attachment.id',
    `order_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `original_file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `file_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `file_size` bigint NOT NULL,
    `mime_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `document_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `keterangan` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `version_number` bigint NULL DEFAULT 0 COMMENT 'Document version number',
    `is_active` tinyint(1) NULL DEFAULT 1,
    `uploaded_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `uploaded_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `change_type` enum(
        'CREATED',
        'UPDATED',
        'DELETED'
    ) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `changed_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `changed_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `change_reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `updated_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `uploaded_by` (`uploaded_by` ASC) USING BTREE,
    INDEX `changed_by` (`changed_by` ASC) USING BTREE,
    INDEX `created_by` (`created_by` ASC) USING BTREE,
    INDEX `updated_by` (`updated_by` ASC) USING BTREE,
    INDEX `idx_order_attachment_history_attachment` (`order_attachment_id` ASC) USING BTREE,
    INDEX `idx_order_attachment_history_order` (`order_id` ASC) USING BTREE,
    INDEX `idx_order_attachment_history_document_id` (`document_id` ASC) USING BTREE,
    INDEX `idx_order_attachment_history_changed_at` (`changed_at` ASC) USING BTREE,
    INDEX `idx_order_attachment_history_change_type` (`change_type` ASC) USING BTREE,
    INDEX `idx_order_attachment_history_version` (`version_number` ASC) USING BTREE,
    CONSTRAINT `order_attachment_history_ibfk_1` FOREIGN KEY (`order_attachment_id`) REFERENCES `order_attachment` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `order_attachment_history_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `order_data` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_attachment_history_ibfk_3` FOREIGN KEY (`document_id`) REFERENCES `rf_document` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_attachment_history_ibfk_4` FOREIGN KEY (`uploaded_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_attachment_history_ibfk_5` FOREIGN KEY (`changed_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_attachment_history_ibfk_6` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_attachment_history_ibfk_7` FOREIGN KEY (`updated_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'History of all changes to order attachments' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_attachment_history
-- ----------------------------

-- ----------------------------
-- Table structure for order_audit_trail
-- ----------------------------
DROP TABLE IF EXISTS `order_audit_trail`;

CREATE TABLE `order_audit_trail` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `order_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `table_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `operation_type` enum('INSERT', 'UPDATE', 'DELETE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `column_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `old_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `new_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `changed_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `changed_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `ip_address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `user_agent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_order_audit_trail_order_id` (`order_id` ASC) USING BTREE,
    INDEX `idx_order_audit_trail_table_name` (`table_name` ASC) USING BTREE,
    INDEX `idx_order_audit_trail_operation_type` (`operation_type` ASC) USING BTREE,
    INDEX `idx_order_audit_trail_changed_by` (`changed_by` ASC) USING BTREE,
    INDEX `idx_order_audit_trail_changed_at` (`changed_at` ASC) USING BTREE,
    INDEX `idx_order_audit_trail_order_changed` (
        `order_id` ASC,
        `changed_at` ASC
    ) USING BTREE,
    CONSTRAINT `order_audit_trail_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_data` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_audit_trail_ibfk_2` FOREIGN KEY (`changed_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Audit trail komprehensif untuk semua perubahan order' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_audit_trail
-- ----------------------------

-- ----------------------------
-- Table structure for order_comment
-- ----------------------------
DROP TABLE IF EXISTS `order_comment`;

CREATE TABLE `order_comment` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `order_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `comment_type` enum(
        'INTERNAL',
        'EXTERNAL',
        'SYSTEM'
    ) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `comment_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `parent_comment_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `is_edited` tinyint(1) NULL DEFAULT 0,
    `edited_at` timestamp NULL DEFAULT NULL,
    `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT 'Soft delete',
    `deleted_at` timestamp NULL DEFAULT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_order_comment_order_id` (`order_id` ASC) USING BTREE,
    INDEX `idx_order_comment_user_id` (`user_id` ASC) USING BTREE,
    INDEX `idx_order_comment_type` (`comment_type` ASC) USING BTREE,
    INDEX `idx_order_comment_created_at` (`created_at` ASC) USING BTREE,
    INDEX `idx_order_comment_parent` (`parent_comment_id` ASC) USING BTREE,
    INDEX `idx_order_comment_is_deleted` (`is_deleted` ASC) USING BTREE,
    INDEX `idx_order_comment_order_created` (
        `order_id` ASC,
        `created_at` ASC
    ) USING BTREE,
    CONSTRAINT `order_comment_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_data` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_comment_ibfk_3` FOREIGN KEY (`parent_comment_id`) REFERENCES `order_comment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Komentar dan kolaborasi untuk order dengan soft delete' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_comment
-- ----------------------------

-- ----------------------------
-- Table structure for order_data
-- ----------------------------
DROP TABLE IF EXISTS `order_data`;

CREATE TABLE `order_data` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `order_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `client_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `client_trade_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `client_contact_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `client_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `client_phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `requestor_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `requestor_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `requestor_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `requestor_department` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `cooperation_type_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `document_type_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `cooperation_period` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `employment_status_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `service_cost_type_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `service_cost_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `payment_terms` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `tax_info` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `penalty_clause` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `contract_value` decimal(15, 2) NULL DEFAULT NULL,
    `document_source_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `additional_notes` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `current_status_code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `workflow_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `priority_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `assigned_to` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `current_role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Current role in workflow process',
    `submission_date` date NULL DEFAULT NULL,
    `deadline_date` date NULL DEFAULT NULL,
    `completion_date` date NULL DEFAULT NULL,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT 'Soft delete',
    `deleted_at` timestamp NULL DEFAULT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `updated_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `order_number` (`order_number` ASC) USING BTREE,
    INDEX `employment_status_id` (`employment_status_id` ASC) USING BTREE,
    INDEX `service_cost_type_id` (`service_cost_type_id` ASC) USING BTREE,
    INDEX `document_source_id` (`document_source_id` ASC) USING BTREE,
    INDEX `created_by` (`created_by` ASC) USING BTREE,
    INDEX `updated_by` (`updated_by` ASC) USING BTREE,
    INDEX `idx_order_data_order_number` (`order_number` ASC) USING BTREE,
    INDEX `idx_order_data_client_name` (`client_name` ASC) USING BTREE,
    INDEX `idx_order_data_requestor_id` (`requestor_id` ASC) USING BTREE,
    INDEX `idx_order_data_current_status` (`current_status_code` ASC) USING BTREE,
    INDEX `idx_order_data_workflow` (`workflow_id` ASC) USING BTREE,
    INDEX `idx_order_data_priority` (`priority_id` ASC) USING BTREE,
    INDEX `idx_order_data_submission_date` (`submission_date` ASC) USING BTREE,
    INDEX `idx_order_data_deadline_date` (`deadline_date` ASC) USING BTREE,
    INDEX `idx_order_data_assigned_to` (`assigned_to` ASC) USING BTREE,
    INDEX `idx_order_data_current_role` (`current_role` ASC) USING BTREE,
    INDEX `idx_order_data_created_at` (`created_at` ASC) USING BTREE,
    INDEX `idx_order_data_is_deleted` (`is_deleted` ASC) USING BTREE,
    INDEX `idx_order_data_cooperation_type` (`cooperation_type_id` ASC) USING BTREE,
    INDEX `idx_order_data_document_type` (`document_type_id` ASC) USING BTREE,
    INDEX `idx_order_data_version` (`version` ASC) USING BTREE,
    INDEX `idx_order_data_status_assigned` (
        `current_status_code` ASC,
        `assigned_to` ASC
    ) USING BTREE,
    INDEX `idx_order_data_client_status` (
        `client_name` ASC,
        `current_status_code` ASC
    ) USING BTREE,
    INDEX `idx_order_data_deadline_status` (
        `deadline_date` ASC,
        `current_status_code` ASC
    ) USING BTREE,
    CONSTRAINT `order_data_ibfk_1` FOREIGN KEY (`cooperation_type_id`) REFERENCES `rf_cooperation_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_data_ibfk_10` FOREIGN KEY (`assigned_to`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_data_ibfk_11` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_data_ibfk_12` FOREIGN KEY (`updated_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_data_ibfk_2` FOREIGN KEY (`document_type_id`) REFERENCES `rf_document_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_data_ibfk_3` FOREIGN KEY (`employment_status_id`) REFERENCES `rf_employment_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_data_ibfk_4` FOREIGN KEY (`service_cost_type_id`) REFERENCES `rf_service_cost_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_data_ibfk_5` FOREIGN KEY (`document_source_id`) REFERENCES `rf_document_source` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_data_ibfk_6` FOREIGN KEY (`current_status_code`) REFERENCES `rf_order_status` (`code`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_data_ibfk_7` FOREIGN KEY (`workflow_id`) REFERENCES `workflow` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_data_ibfk_8` FOREIGN KEY (`priority_id`) REFERENCES `rf_priority` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_data_ibfk_9` FOREIGN KEY (`requestor_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `chk_completion_after_submission` CHECK (
        (`completion_date` is null)
        or (`submission_date` is null)
        or (
            `completion_date` >= `submission_date`
        )
    ),
    CONSTRAINT `chk_contract_value_positive` CHECK (
        (`contract_value` is null)
        or (`contract_value` >= 0)
    ),
    CONSTRAINT `chk_deadline_after_submission` CHECK (
        (`deadline_date` is null)
        or (`submission_date` is null)
        or (
            `deadline_date` >= `submission_date`
        )
    ),
    CONSTRAINT `chk_version_non_negative` CHECK (`version` >= 0)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Tabel utama order data yang menggantikan permintaan_dokumen - Optimized for JPA' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_data
-- ----------------------------

-- ----------------------------
-- Table structure for order_notification
-- ----------------------------
DROP TABLE IF EXISTS `order_notification`;

CREATE TABLE `order_notification` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `order_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `notification_type` enum(
        'STATUS_CHANGE',
        'ASSIGNMENT',
        'COMMENT',
        'DEADLINE',
        'APPROVAL'
    ) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `is_read` tinyint(1) NULL DEFAULT 0,
    `read_at` timestamp NULL DEFAULT NULL,
    `sent_via_email` tinyint(1) NULL DEFAULT 0,
    `email_sent_at` timestamp NULL DEFAULT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_order_notification_user_id` (`user_id` ASC) USING BTREE,
    INDEX `idx_order_notification_order_id` (`order_id` ASC) USING BTREE,
    INDEX `idx_order_notification_type` (`notification_type` ASC) USING BTREE,
    INDEX `idx_order_notification_is_read` (`is_read` ASC) USING BTREE,
    INDEX `idx_order_notification_created_at` (`created_at` ASC) USING BTREE,
    INDEX `idx_order_notification_user_read` (`user_id` ASC, `is_read` ASC) USING BTREE,
    CONSTRAINT `order_notification_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_data` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_notification_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Sistem notifikasi untuk stakeholder communication' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_notification
-- ----------------------------

-- ----------------------------
-- Table structure for order_number_sequence
-- ----------------------------
DROP TABLE IF EXISTS `order_number_sequence`;

CREATE TABLE `order_number_sequence` (
    `seq_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `seq_value` bigint NOT NULL DEFAULT 1,
    PRIMARY KEY (`seq_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_number_sequence
-- ----------------------------
INSERT INTO `order_number_sequence` VALUES ('order_number_seq', 1);

-- ----------------------------
-- Table structure for order_status_history
-- ----------------------------
DROP TABLE IF EXISTS `order_status_history`;

CREATE TABLE `order_status_history` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `order_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `from_status_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `to_status_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `changed_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `change_reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `changed_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `from_status_id` (`from_status_id` ASC) USING BTREE,
    INDEX `to_status_id` (`to_status_id` ASC) USING BTREE,
    INDEX `idx_order_status_history_order_id` (`order_id` ASC) USING BTREE,
    INDEX `idx_order_status_history_changed_at` (`changed_at` ASC) USING BTREE,
    INDEX `idx_order_status_history_changed_by` (`changed_by` ASC) USING BTREE,
    INDEX `idx_order_status_history_order_changed` (
        `order_id` ASC,
        `changed_at` ASC
    ) USING BTREE,
    CONSTRAINT `order_status_history_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_data` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_status_history_ibfk_2` FOREIGN KEY (`from_status_id`) REFERENCES `rf_order_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_status_history_ibfk_3` FOREIGN KEY (`to_status_id`) REFERENCES `rf_order_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `order_status_history_ibfk_4` FOREIGN KEY (`changed_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'History perubahan status untuk setiap order' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_status_history
-- ----------------------------

-- ----------------------------
-- Table structure for rf_cooperation_type
-- ----------------------------
DROP TABLE IF EXISTS `rf_cooperation_type`;

CREATE TABLE `rf_cooperation_type` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `is_active` tinyint(1) NULL DEFAULT 1,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_rf_cooperation_type_is_active` (`is_active` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Tabel referensi jenis kerjasama (enhanced dari rf_jenis_kerjasama)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rf_cooperation_type
-- ----------------------------

-- ----------------------------
-- Table structure for rf_document
-- ----------------------------
DROP TABLE IF EXISTS `rf_document`;

CREATE TABLE `rf_document` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `document_source_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `deskripsi` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `is_active` tinyint(1) NULL DEFAULT 1,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `updated_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `created_by` (`created_by` ASC) USING BTREE,
    INDEX `updated_by` (`updated_by` ASC) USING BTREE,
    INDEX `idx_rf_document_document_source` (`document_source_id` ASC) USING BTREE,
    INDEX `idx_rf_document_name` (`name` ASC) USING BTREE,
    INDEX `idx_rf_document_is_active` (`is_active` ASC) USING BTREE,
    CONSTRAINT `rf_document_ibfk_1` FOREIGN KEY (`document_source_id`) REFERENCES `rf_document_source` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `rf_document_ibfk_2` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `rf_document_ibfk_3` FOREIGN KEY (`updated_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Tabel referensi dokumen dengan sumber dokumen' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rf_document
-- ----------------------------
INSERT INTO
    `rf_document`
VALUES (
        '0ffd02f2-b5c8-11f0-93cd-00090ffe0001',
        '996b8a71-b5c7-11f0-93cd-00090ffe0001',
        'Deviasi',
        NULL,
        1,
        '2025-10-31 02:38:51',
        '2025-10-31 02:38:51',
        NULL,
        NULL,
        0
    );

INSERT INTO
    `rf_document`
VALUES (
        '107a25f4-b5c8-11f0-93cd-00090ffe0001',
        '996b8a71-b5c7-11f0-93cd-00090ffe0001',
        'SPK',
        NULL,
        1,
        '2025-10-31 02:38:52',
        '2025-10-31 02:38:52',
        NULL,
        NULL,
        0
    );

INSERT INTO
    `rf_document`
VALUES (
        '10e98aa7-b5c8-11f0-93cd-00090ffe0001',
        '996b8a71-b5c7-11f0-93cd-00090ffe0001',
        'Draft Client',
        NULL,
        1,
        '2025-10-31 02:38:53',
        '2025-10-31 02:38:53',
        NULL,
        NULL,
        0
    );

INSERT INTO
    `rf_document`
VALUES (
        '1faf317f-b5c8-11f0-93cd-00090ffe0001',
        '99e9ab54-b5c7-11f0-93cd-00090ffe0001',
        'Deviasi',
        NULL,
        1,
        '2025-10-31 02:39:17',
        '2025-10-31 02:39:17',
        NULL,
        NULL,
        0
    );

INSERT INTO
    `rf_document`
VALUES (
        '203bed8c-b5c8-11f0-93cd-00090ffe0001',
        '99e9ab54-b5c7-11f0-93cd-00090ffe0001',
        'SPK',
        NULL,
        1,
        '2025-10-31 02:39:18',
        '2025-10-31 02:39:18',
        NULL,
        NULL,
        0
    );

-- ----------------------------
-- Table structure for rf_document_source
-- ----------------------------
DROP TABLE IF EXISTS `rf_document_source`;

CREATE TABLE `rf_document_source` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `is_active` tinyint(1) NULL DEFAULT 1,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_rf_document_source_is_active` (`is_active` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Tabel referensi sumber dokumen (enhanced dari rf_sumber_dokumen)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rf_document_source
-- ----------------------------
INSERT INTO
    `rf_document_source`
VALUES (
        '996b8a71-b5c7-11f0-93cd-00090ffe0001',
        'DRAFT KLIEN',
        NULL,
        1,
        '2025-10-31 02:35:32',
        '2025-10-31 02:35:32',
        0
    );

INSERT INTO
    `rf_document_source`
VALUES (
        '99e9ab54-b5c7-11f0-93cd-00090ffe0001',
        'DRAFT PT DIKA',
        NULL,
        1,
        '2025-10-31 02:35:33',
        '2025-10-31 02:35:33',
        0
    );

-- ----------------------------
-- Table structure for rf_document_type
-- ----------------------------
DROP TABLE IF EXISTS `rf_document_type`;

CREATE TABLE `rf_document_type` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `template_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `is_active` tinyint(1) NULL DEFAULT 1,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_rf_document_type_is_active` (`is_active` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Tabel referensi jenis dokumen (enhanced dari rf_jenis_dokumen)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rf_document_type
-- ----------------------------

-- ----------------------------
-- Table structure for rf_employment_status
-- ----------------------------
DROP TABLE IF EXISTS `rf_employment_status`;

CREATE TABLE `rf_employment_status` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `is_active` tinyint(1) NULL DEFAULT 1,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_rf_employment_status_is_active` (`is_active` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Tabel referensi status tenaga kerja (enhanced dari rf_status_tenaga_kerja)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rf_employment_status
-- ----------------------------
INSERT INTO
    `rf_employment_status`
VALUES (
        '68868116-b5c7-11f0-93cd-00090ffe0001',
        'PKWT',
        NULL,
        1,
        '2025-10-31 02:34:10',
        '2025-10-31 02:34:10',
        0
    );

INSERT INTO
    `rf_employment_status`
VALUES (
        '691be668-b5c7-11f0-93cd-00090ffe0001',
        'MITRA',
        NULL,
        1,
        '2025-10-31 02:34:11',
        '2025-10-31 02:34:11',
        0
    );

INSERT INTO
    `rf_employment_status`
VALUES (
        '69a37a74-b5c7-11f0-93cd-00090ffe0001',
        'MAGANG',
        NULL,
        1,
        '2025-10-31 02:34:12',
        '2025-10-31 02:34:12',
        0
    );

INSERT INTO
    `rf_employment_status`
VALUES (
        '6b4497e1-b5c7-11f0-93cd-00090ffe0001',
        'PEKERJA HARIAN LEPAS',
        NULL,
        1,
        '2025-10-31 02:34:15',
        '2025-10-31 02:34:15',
        0
    );

-- ----------------------------
-- Table structure for rf_order_status
-- ----------------------------
DROP TABLE IF EXISTS `rf_order_status`;

CREATE TABLE `rf_order_status` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `color_code` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Hex color code untuk UI',
    `is_active` tinyint(1) NULL DEFAULT 1,
    `sequence_order` int NOT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `code` (`code` ASC) USING BTREE,
    INDEX `idx_rf_order_status_code` (`code` ASC) USING BTREE,
    INDEX `idx_rf_order_status_is_active` (`is_active` ASC) USING BTREE,
    INDEX `idx_rf_order_status_sequence` (`sequence_order` ASC) USING BTREE,
    CONSTRAINT `chk_sequence_positive` CHECK (`sequence_order` > 0)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Tabel referensi status order dengan workflow management' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rf_order_status
-- ----------------------------
INSERT INTO
    `rf_order_status`
VALUES (
        '2e79b5bd-b529-11f0-8db2-00090ffe0001',
        'DRAFT',
        'Draft',
        'Order dalam tahap persiapan',
        '#6B7280',
        1,
        1,
        '2025-10-30 07:41:32',
        '2025-10-30 07:41:32',
        0
    );

INSERT INTO
    `rf_order_status`
VALUES (
        '2e79c06d-b529-11f0-8db2-00090ffe0001',
        'SUBMITTED',
        'Submitted',
        'Order telah disubmit untuk review',
        '#3B82F6',
        1,
        2,
        '2025-10-30 07:41:32',
        '2025-10-30 07:41:32',
        0
    );

INSERT INTO
    `rf_order_status`
VALUES (
        '2e79cab8-b529-11f0-8db2-00090ffe0001',
        'REVIEW',
        'Under Review',
        'Order sedang dalam proses review',
        '#F59E0B',
        1,
        3,
        '2025-10-30 07:41:32',
        '2025-10-30 07:41:32',
        0
    );

INSERT INTO
    `rf_order_status`
VALUES (
        '2e79cfa2-b529-11f0-8db2-00090ffe0001',
        'APPROVED',
        'Approved',
        'Order telah disetujui',
        '#10B981',
        1,
        4,
        '2025-10-30 07:41:32',
        '2025-10-30 07:41:32',
        0
    );

INSERT INTO
    `rf_order_status`
VALUES (
        '2e79d319-b529-11f0-8db2-00090ffe0001',
        'REJECTED',
        'Rejected',
        'Order ditolak',
        '#EF4444',
        1,
        5,
        '2025-10-30 07:41:32',
        '2025-10-30 07:41:32',
        0
    );

INSERT INTO
    `rf_order_status`
VALUES (
        '2e79d624-b529-11f0-8db2-00090ffe0001',
        'IN_PROGRESS',
        'In Progress',
        'Order sedang diproses',
        '#8B5CF6',
        1,
        6,
        '2025-10-30 07:41:32',
        '2025-10-30 07:41:32',
        0
    );

INSERT INTO
    `rf_order_status`
VALUES (
        '2e79d8fb-b529-11f0-8db2-00090ffe0001',
        'ON_HOLD',
        'On Hold',
        'Order ditunda sementara',
        '#F59E0B',
        1,
        7,
        '2025-10-30 07:41:32',
        '2025-10-30 07:41:32',
        0
    );

INSERT INTO
    `rf_order_status`
VALUES (
        '2e79dbc5-b529-11f0-8db2-00090ffe0001',
        'COMPLETED',
        'Completed',
        'Order telah selesai',
        '#10B981',
        1,
        8,
        '2025-10-30 07:41:32',
        '2025-10-30 07:41:32',
        0
    );

INSERT INTO
    `rf_order_status`
VALUES (
        '2e79dfc7-b529-11f0-8db2-00090ffe0001',
        'CANCELLED',
        'Cancelled',
        'Order dibatalkan',
        '#EF4444',
        1,
        9,
        '2025-10-30 07:41:32',
        '2025-10-30 07:41:32',
        0
    );

-- ----------------------------
-- Table structure for rf_priority
-- ----------------------------
DROP TABLE IF EXISTS `rf_priority`;

CREATE TABLE `rf_priority` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `color_code` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `default_deadline_days` int NULL DEFAULT NULL,
    `is_active` tinyint(1) NULL DEFAULT 1,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `code` (`code` ASC) USING BTREE,
    INDEX `idx_rf_priority_code` (`code` ASC) USING BTREE,
    INDEX `idx_rf_priority_is_active` (`is_active` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Tabel referensi priority level untuk order' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rf_priority
-- ----------------------------
INSERT INTO
    `rf_priority`
VALUES (
        '2e7abab6-b529-11f0-8db2-00090ffe0001',
        'LOW',
        'Low',
        'Prioritas rendah',
        '#6B7280',
        14,
        1,
        '2025-10-30 07:41:32',
        '2025-10-30 07:41:32',
        0
    );

INSERT INTO
    `rf_priority`
VALUES (
        '2e7ac03c-b529-11f0-8db2-00090ffe0001',
        'NORMAL',
        'Normal',
        'Prioritas normal',
        '#3B82F6',
        7,
        1,
        '2025-10-30 07:41:32',
        '2025-10-30 07:41:32',
        0
    );

INSERT INTO
    `rf_priority`
VALUES (
        '2e7ac389-b529-11f0-8db2-00090ffe0001',
        'HIGH',
        'High',
        'Prioritas tinggi',
        '#F59E0B',
        5,
        1,
        '2025-10-30 07:41:32',
        '2025-10-30 07:41:32',
        0
    );

INSERT INTO
    `rf_priority`
VALUES (
        '2e7ac692-b529-11f0-8db2-00090ffe0001',
        'URGENT',
        'Urgent',
        'Prioritas mendesak',
        '#EF4444',
        2,
        1,
        '2025-10-30 07:41:32',
        '2025-10-30 07:41:32',
        0
    );

-- ----------------------------
-- Table structure for rf_service_cost_type
-- ----------------------------
DROP TABLE IF EXISTS `rf_service_cost_type`;

CREATE TABLE `rf_service_cost_type` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `is_active` tinyint(1) NULL DEFAULT 1,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_rf_service_cost_type_is_active` (`is_active` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Tabel referensi biaya jasa (enhanced dari rf_biaya_jasa)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rf_service_cost_type
-- ----------------------------
INSERT INTO
    `rf_service_cost_type`
VALUES (
        '2e995980-b5c7-11f0-93cd-00090ffe0001',
        'Total Biaya Jasa',
        NULL,
        1,
        '2025-10-31 02:32:33',
        '2025-10-31 02:32:33',
        0
    );

INSERT INTO
    `rf_service_cost_type`
VALUES (
        '3c5c9795-b5c7-11f0-93cd-00090ffe0001',
        'Management Fee',
        NULL,
        1,
        '2025-10-31 02:32:56',
        '2025-10-31 02:32:56',
        0
    );

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'BCrypt encoded password',
    `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `full_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'ROLE_ADMIN, ROLE_MANAGER, ROLE_USER, etc.',
    `department` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `position` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `is_active` tinyint(1) NULL DEFAULT 1,
    `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT 'Soft delete',
    `deleted_at` timestamp NULL DEFAULT NULL,
    `last_login` timestamp NULL DEFAULT NULL,
    `failed_login_attempts` int NULL DEFAULT 0,
    `locked_until` timestamp NULL DEFAULT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `username` (`username` ASC) USING BTREE,
    UNIQUE INDEX `email` (`email` ASC) USING BTREE,
    INDEX `idx_users_username` (`username` ASC) USING BTREE,
    INDEX `idx_users_email` (`email` ASC) USING BTREE,
    INDEX `idx_users_role` (`role` ASC) USING BTREE,
    INDEX `idx_users_department` (`department` ASC) USING BTREE,
    INDEX `idx_users_is_active` (`is_active` ASC) USING BTREE,
    INDEX `idx_users_is_deleted` (`is_deleted` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Enhanced users table untuk order data system dengan Spring Security' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO
    `users`
VALUES (
        'f54c1a38-4a90-4241-9ffd-ba15c99f0dcb',
        'legal',
        '$2a$10$b.0S5VqFqBfGGSQmqigxGuxWSzasJSLiDIxLtRDMk20qAHjrcpxR.',
        'user@example.com',
        'user legal',
        'USER',
        'LEGAL',
        'LEGAL',
        '+1234567890',
        1,
        0,
        NULL,
        '2025-10-31 01:43:43',
        0,
        NULL,
        NULL,
        NULL,
        8
    );

-- ----------------------------
-- Table structure for workflow
-- ----------------------------
DROP TABLE IF EXISTS `workflow`;

CREATE TABLE `workflow` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `nama` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `deskripsi` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
    `is_active` tinyint(1) NULL DEFAULT 1,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `updated_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `created_by` (`created_by` ASC) USING BTREE,
    INDEX `updated_by` (`updated_by` ASC) USING BTREE,
    INDEX `idx_workflow_is_active` (`is_active` ASC) USING BTREE,
    CONSTRAINT `workflow_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `workflow_ibfk_2` FOREIGN KEY (`updated_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Definisi workflow' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of workflow
-- ----------------------------
INSERT INTO
    `workflow`
VALUES (
        '98478187-b5c8-11f0-93cd-00090ffe0001',
        'LDR',
        NULL,
        1,
        '2025-10-31 02:42:40',
        '2025-10-31 02:42:40',
        NULL,
        NULL,
        0
    );

-- ----------------------------
-- Table structure for workflow_detail
-- ----------------------------
DROP TABLE IF EXISTS `workflow_detail`;

CREATE TABLE `workflow_detail` (
    `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT(uuid()),
    `workflow_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `current_stage` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Free text stage saat ini',
    `next_stage` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Free text stage berikutnya (approve)',
    `return_stage` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Free text stage untuk return/rollback',
    `reject_stage` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Free text stage untuk reject',
    `sla` int NULL DEFAULT NULL COMMENT 'Service Level Agreement in hours',
    `is_active` tinyint(1) NULL DEFAULT 1,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `updated_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `version` bigint NULL DEFAULT 0 COMMENT 'Optimistic locking version',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_workflow_detail_current` (
        `workflow_id` ASC,
        `current_stage` ASC
    ) USING BTREE,
    INDEX `created_by` (`created_by` ASC) USING BTREE,
    INDEX `updated_by` (`updated_by` ASC) USING BTREE,
    INDEX `idx_workflow_detail_workflow` (`workflow_id` ASC) USING BTREE,
    INDEX `idx_workflow_detail_current_stage` (`current_stage` ASC) USING BTREE,
    INDEX `idx_workflow_detail_is_active` (`is_active` ASC) USING BTREE,
    INDEX `idx_workflow_detail_sla` (`sla` ASC) USING BTREE,
    CONSTRAINT `workflow_detail_ibfk_1` FOREIGN KEY (`workflow_id`) REFERENCES `workflow` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `workflow_detail_ibfk_2` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `workflow_detail_ibfk_3` FOREIGN KEY (`updated_by`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Detail state transitions untuk workflow' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of workflow_detail
-- ----------------------------
INSERT INTO
    `workflow_detail`
VALUES (
        'f98d92ed-b5c8-11f0-93cd-00090ffe0001',
        '98478187-b5c8-11f0-93cd-00090ffe0001',
        'legal',
        'bd',
        'legal',
        'legal',
        NULL,
        1,
        '2025-10-31 02:45:23',
        '2025-10-31 02:45:23',
        NULL,
        NULL,
        0
    );

INSERT INTO
    `workflow_detail`
VALUES (
        'fa707444-b5c8-11f0-93cd-00090ffe0001',
        '98478187-b5c8-11f0-93cd-00090ffe0001',
        'bd',
        'legal',
        'bd',
        'bd',
        NULL,
        1,
        '2025-10-31 02:45:24',
        '2025-10-31 02:45:24',
        NULL,
        NULL,
        0
    );

SET FOREIGN_KEY_CHECKS = 1;
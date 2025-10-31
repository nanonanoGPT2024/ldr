-- =====================================================
-- FINAL SCHEMA ORDER DATA FOR SPRINGBOOT
-- Optimized for JPA/Hibernate compatibility
-- =====================================================
-- Author: Kilo Code
-- Date: 2025-10-29
-- Database: MySQL/MariaDB
-- Framework: SpringBoot with JPA/Hibernate
-- =====================================================

-- Start transaction untuk memastikan konsistensi
START TRANSACTION;

-- =====================================================
-- 1. TABEL REFERENSI (ENHANCED FOR SPRINGBOOT)
-- =====================================================

-- 1.1 Tabel Status Order
-- @Entity @Table(name = "rf_order_status")
CREATE TABLE rf_order_status (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    color_code VARCHAR(7) COMMENT 'Hex color code untuk UI',
    is_active BOOLEAN DEFAULT TRUE,
    sequence_order INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',

    INDEX idx_rf_order_status_code (code),
    INDEX idx_rf_order_status_is_active (is_active),
    INDEX idx_rf_order_status_sequence (sequence_order)
) COMMENT 'Tabel referensi status order dengan workflow management';

-- 1.2 Tabel Priority
-- @Entity @Table(name = "rf_priority")
CREATE TABLE rf_priority (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    color_code VARCHAR(7),
    default_deadline_days INT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',

    INDEX idx_rf_priority_code (code),
    INDEX idx_rf_priority_is_active (is_active)
) COMMENT 'Tabel referensi priority level untuk order';

-- 1.3 Enhanced Cooperation Type
-- @Entity @Table(name = "rf_cooperation_type")
CREATE TABLE rf_cooperation_type (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',

    INDEX idx_rf_cooperation_type_is_active (is_active)
) COMMENT 'Tabel referensi jenis kerjasama (enhanced dari rf_jenis_kerjasama)';

-- 1.4 Enhanced Document Type
-- @Entity @Table(name = "rf_document_type")
CREATE TABLE rf_document_type (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    template_path VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',

    INDEX idx_rf_document_type_is_active (is_active)
) COMMENT 'Tabel referensi jenis dokumen (enhanced dari rf_jenis_dokumen)';

-- 1.5 Enhanced Employment Status
-- @Entity @Table(name = "rf_employment_status")
CREATE TABLE rf_employment_status (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',

    INDEX idx_rf_employment_status_is_active (is_active)
) COMMENT 'Tabel referensi status tenaga kerja (enhanced dari rf_status_tenaga_kerja)';

-- 1.6 Enhanced Service Cost Type
-- @Entity @Table(name = "rf_service_cost_type")
CREATE TABLE rf_service_cost_type (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',

    INDEX idx_rf_service_cost_type_is_active (is_active)
) COMMENT 'Tabel referensi biaya jasa (enhanced dari rf_biaya_jasa)';

-- 1.7 Enhanced Document Source
-- @Entity @Table(name = "rf_document_source")
CREATE TABLE rf_document_source (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',

    INDEX idx_rf_document_source_is_active (is_active)
) COMMENT 'Tabel referensi sumber dokumen (enhanced dari rf_sumber_dokumen)';

-- =====================================================
-- 2. TABEL USERS (ENHANCED FOR SPRINGBOOT SECURITY)
-- =====================================================

-- 2.1 Enhanced Users Table
-- @Entity @Table(name = "users")
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL COMMENT 'BCrypt encoded password',
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL COMMENT 'ROLE_ADMIN, ROLE_MANAGER, ROLE_USER, etc.',
    department VARCHAR(100),
    position VARCHAR(100),
    phone VARCHAR(20),
    is_active BOOLEAN DEFAULT TRUE,
    is_deleted BOOLEAN DEFAULT FALSE COMMENT 'Soft delete',
    deleted_at TIMESTAMP NULL,
    last_login TIMESTAMP NULL,
    failed_login_attempts INT DEFAULT 0,
    locked_until TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',

    INDEX idx_users_username (username),
    INDEX idx_users_email (email),
    INDEX idx_users_role (role),
    INDEX idx_users_department (department),
    INDEX idx_users_is_active (is_active),
    INDEX idx_users_is_deleted (is_deleted)
) COMMENT 'Enhanced users table untuk order data system dengan Spring Security';

-- =====================================================
-- 3. TABEL REFERENSI YANG MEREFERENSI REFERENCE TABLES
-- =====================================================

-- 3.1 Enhanced Document Table (mereferensi rf_document_source)
-- @Entity @Table(name = "rf_document")
CREATE TABLE rf_document (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    document_source_id VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    deskripsi TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(36),
    updated_by VARCHAR(36),
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',

    FOREIGN KEY (document_source_id) REFERENCES rf_document_source(id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id),
    INDEX idx_rf_document_document_source (document_source_id),
    INDEX idx_rf_document_name (name),
    INDEX idx_rf_document_is_active (is_active)
) COMMENT 'Tabel referensi dokumen dengan sumber dokumen';

-- =====================================================
-- 4. TABEL WORKFLOW DEFINITION
-- =====================================================

-- 4.1 Tabel Workflow Definition (mereferensi users)
-- @Entity @Table(name = "workflow")
CREATE TABLE workflow (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    nama VARCHAR(255) NOT NULL,
    deskripsi TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(36),
    updated_by VARCHAR(36),
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',

    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id),
    INDEX idx_workflow_is_active (is_active)
) COMMENT 'Definisi workflow';

-- =====================================================
-- 5. TABEL UTAMA ORDER DATA (OPTIMIZED FOR JPA)
-- =====================================================

-- 4.1 Tabel Order Data (menggantikan permintaan_dokumen)
-- @Entity @Table(name = "order_data")
CREATE TABLE order_data (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    order_number VARCHAR(50) UNIQUE NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,

    -- Informasi Klien
    client_name VARCHAR(255) NOT NULL,
    client_trade_name VARCHAR(255),
    client_contact_person VARCHAR(255),
    client_email VARCHAR(255),
    client_phone VARCHAR(50),

    -- Informasi Requestor
    requestor_id VARCHAR(36) NOT NULL,
    requestor_name VARCHAR(255) NOT NULL,
    requestor_email VARCHAR(255),
    requestor_department VARCHAR(100),

    -- Detail Kerjasama
    cooperation_type_id VARCHAR(36) NOT NULL,
    document_type_id VARCHAR(36) NOT NULL,
    cooperation_period VARCHAR(100),
    employment_status_id VARCHAR(36),
    position VARCHAR(255),

    -- Financial Information
    service_cost_type_id VARCHAR(36),
    service_cost_description TEXT,
    payment_terms VARCHAR(255),
    tax_info VARCHAR(100),
    penalty_clause VARCHAR(255),
    contract_value DECIMAL(15,2),

    -- Document Source
    document_source_id VARCHAR(36),
    additional_notes TEXT,

    -- Workflow & Status
    current_status_id VARCHAR(36) NOT NULL,
    workflow_id VARCHAR(36),
    priority_id VARCHAR(36) NOT NULL,
    assigned_to VARCHAR(36),
    current_role VARCHAR(50) COMMENT 'Current role in workflow process',

    -- Timeline Management
    submission_date DATE,
    deadline_date DATE,
    completion_date DATE,

    -- Metadata untuk SpringBoot/JPA
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',
    is_deleted BOOLEAN DEFAULT FALSE COMMENT 'Soft delete',
    deleted_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(36),
    updated_by VARCHAR(36),

    -- Foreign Keys
    FOREIGN KEY (cooperation_type_id) REFERENCES rf_cooperation_type(id),
    FOREIGN KEY (document_type_id) REFERENCES rf_document_type(id),
    FOREIGN KEY (employment_status_id) REFERENCES rf_employment_status(id),
    FOREIGN KEY (service_cost_type_id) REFERENCES rf_service_cost_type(id),
    FOREIGN KEY (document_source_id) REFERENCES rf_document_source(id),
    FOREIGN KEY (current_status_id) REFERENCES rf_order_status(id),
    FOREIGN KEY (workflow_id) REFERENCES workflow(id),
    FOREIGN KEY (priority_id) REFERENCES rf_priority(id),
    FOREIGN KEY (requestor_id) REFERENCES users(id),
    FOREIGN KEY (assigned_to) REFERENCES users(id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id),

    -- Index untuk performa (optimized untuk SpringBoot queries)
    INDEX idx_order_data_order_number (order_number),
    INDEX idx_order_data_client_name (client_name),
    INDEX idx_order_data_requestor_id (requestor_id),
    INDEX idx_order_data_current_status (current_status_id),
    INDEX idx_order_data_workflow (workflow_id),
    INDEX idx_order_data_priority (priority_id),
    INDEX idx_order_data_submission_date (submission_date),
    INDEX idx_order_data_deadline_date (deadline_date),
    INDEX idx_order_data_assigned_to (assigned_to),
    INDEX idx_order_data_current_role (current_role),
    INDEX idx_order_data_created_at (created_at),
    INDEX idx_order_data_is_deleted (is_deleted),
    INDEX idx_order_data_cooperation_type (cooperation_type_id),
    INDEX idx_order_data_document_type (document_type_id),
    INDEX idx_order_data_version (version),

    -- Composite indexes untuk common query patterns
    INDEX idx_order_data_status_assigned (current_status_id, assigned_to),
    INDEX idx_order_data_client_status (client_name, current_status_id),
    INDEX idx_order_data_deadline_status (deadline_date, current_status_id)
) COMMENT 'Tabel utama order data yang menggantikan permintaan_dokumen - Optimized for JPA';

-- =====================================================
-- 6. TABEL WORKFLOW & APPROVAL
-- =====================================================

-- 5.1 Tabel Approval Tracking
-- @Entity @Table(name = "order_approval")
CREATE TABLE order_approval (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    order_id VARCHAR(36) NOT NULL,
    approver_id VARCHAR(36) NOT NULL,
    approver_role VARCHAR(50) NOT NULL,
    status ENUM('REQUEST', 'APPROVED', 'REJECTED', 'RETURN') DEFAULT 'REQUEST',
    comments TEXT,
    approved_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',

    FOREIGN KEY (order_id) REFERENCES order_data(id),
    FOREIGN KEY (approver_id) REFERENCES users(id),

    INDEX idx_order_approval_order_id (order_id),
    INDEX idx_order_approval_approver_id (approver_id),
    INDEX idx_order_approval_status (status)
) COMMENT 'Tracking approval untuk setiap order';

-- 5.2 Tabel Workflow Detail (State Transitions)
-- @Entity @Table(name = "workflow_detail")
CREATE TABLE workflow_detail (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    workflow_id VARCHAR(36) NOT NULL,
    current_stage VARCHAR(36) NOT NULL COMMENT 'Free text stage saat ini',
    next_stage VARCHAR(36) NULL COMMENT 'Free text stage berikutnya (approve)',
    return_stage VARCHAR(36) NULL COMMENT 'Free text stage untuk return/rollback',
    reject_stage VARCHAR(36) NULL COMMENT 'Free text stage untuk reject',
    sla INT NULL COMMENT 'Service Level Agreement in hours',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(36),
    updated_by VARCHAR(36),
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',

    FOREIGN KEY (workflow_id) REFERENCES workflow(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id),

    INDEX idx_workflow_detail_workflow (workflow_id),
    INDEX idx_workflow_detail_current_stage (current_stage),
    INDEX idx_workflow_detail_is_active (is_active),
    INDEX idx_workflow_detail_sla (sla),
    UNIQUE KEY uk_workflow_detail_current (workflow_id, current_stage)
) COMMENT 'Detail state transitions untuk workflow';

-- =====================================================
-- 7. TABEL STATUS HISTORY & TRACKING
-- =====================================================

-- 5.1 Tabel Status History
-- @Entity @Table(name = "order_status_history")
CREATE TABLE order_status_history (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    order_id VARCHAR(36) NOT NULL,
    from_status_id VARCHAR(36),
    to_status_id VARCHAR(36) NOT NULL,
    changed_by VARCHAR(36) NOT NULL,
    change_reason TEXT,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (order_id) REFERENCES order_data(id),
    FOREIGN KEY (from_status_id) REFERENCES rf_order_status(id),
    FOREIGN KEY (to_status_id) REFERENCES rf_order_status(id),
    FOREIGN KEY (changed_by) REFERENCES users(id),
    
    INDEX idx_order_status_history_order_id (order_id),
    INDEX idx_order_status_history_changed_at (changed_at),
    INDEX idx_order_status_history_changed_by (changed_by),
    INDEX idx_order_status_history_order_changed (order_id, changed_at)
) COMMENT 'History perubahan status untuk setiap order';

-- 5.2 Tabel Assignment History
-- @Entity @Table(name = "order_assignment_history")
CREATE TABLE order_assignment_history (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    order_id VARCHAR(36) NOT NULL,
    assigned_to VARCHAR(36) NOT NULL,
    assigned_by VARCHAR(36) NOT NULL,
    assignment_type ENUM('ASSIGNED', 'REASSIGNED', 'UNASSIGNED') NOT NULL,
    notes TEXT,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (order_id) REFERENCES order_data(id),
    FOREIGN KEY (assigned_to) REFERENCES users(id),
    FOREIGN KEY (assigned_by) REFERENCES users(id),
    
    INDEX idx_order_assignment_history_order_id (order_id),
    INDEX idx_order_assignment_history_assigned_to (assigned_to),
    INDEX idx_order_assignment_history_assigned_at (assigned_at),
    INDEX idx_order_assignment_history_order_assigned (order_id, assigned_at)
) COMMENT 'History perubahan assignment untuk setiap order';

-- =====================================================
-- 8. TABEL COMMENT & COLLABORATION
-- =====================================================

-- 6.1 Tabel Comments
-- @Entity @Table(name = "order_comment")
CREATE TABLE order_comment (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    order_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    comment_type ENUM('INTERNAL', 'EXTERNAL', 'SYSTEM') NOT NULL,
    comment_text TEXT NOT NULL,
    parent_comment_id VARCHAR(36),
    is_edited BOOLEAN DEFAULT FALSE,
    edited_at TIMESTAMP NULL,
    is_deleted BOOLEAN DEFAULT FALSE COMMENT 'Soft delete',
    deleted_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',
    
    FOREIGN KEY (order_id) REFERENCES order_data(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (parent_comment_id) REFERENCES order_comment(id),
    
    INDEX idx_order_comment_order_id (order_id),
    INDEX idx_order_comment_user_id (user_id),
    INDEX idx_order_comment_type (comment_type),
    INDEX idx_order_comment_created_at (created_at),
    INDEX idx_order_comment_parent (parent_comment_id),
    INDEX idx_order_comment_is_deleted (is_deleted),
    INDEX idx_order_comment_order_created (order_id, created_at)
) COMMENT 'Komentar dan kolaborasi untuk order dengan soft delete';

-- =====================================================
-- 9. TABEL ATTACHMENT MANAGEMENT
-- =====================================================

-- 7.1 Enhanced Tabel Attachments
-- @Entity @Table(name = "order_attachment")
CREATE TABLE order_attachment (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    order_id VARCHAR(36) NOT NULL,
    document_id VARCHAR(36) NULL,
    keterangan TEXT,
    file_name VARCHAR(255) NOT NULL,
    original_file_name VARCHAR(255) NOT NULL COMMENT 'Original file name before upload',
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT,
    file_type VARCHAR(100),
    mime_type VARCHAR(100),
    uploaded_by VARCHAR(36) NOT NULL,
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',
    is_active BOOLEAN DEFAULT TRUE,
    is_deleted BOOLEAN DEFAULT FALSE COMMENT 'Soft delete',
    deleted_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (order_id) REFERENCES order_data(id),
    FOREIGN KEY (document_id) REFERENCES rf_document(id),
    FOREIGN KEY (uploaded_by) REFERENCES users(id),

    INDEX idx_order_attachment_order_id (order_id),
    INDEX idx_order_attachment_document_id (document_id),
    INDEX idx_order_attachment_uploaded_by (uploaded_by),
    INDEX idx_order_attachment_created_at (created_at),
    INDEX idx_order_attachment_is_active (is_active),
    INDEX idx_order_attachment_is_deleted (is_deleted),
    INDEX idx_order_attachment_order_document (order_id, document_id)
) COMMENT 'Management attachment untuk order dengan versioning dan soft delete';
-- 7.2 Tabel Order Attachment History
-- @Entity @Table(name = "order_attachment_history")
CREATE TABLE order_attachment_history (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    order_attachment_id VARCHAR(36) NOT NULL COMMENT 'FK to order_attachment.id',
    order_id VARCHAR(36) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    original_file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT NOT NULL,
    mime_type VARCHAR(100) NOT NULL,
    document_id VARCHAR(36) NULL,
    keterangan TEXT,
    version_number BIGINT DEFAULT 0 COMMENT 'Document version number',
    is_active BOOLEAN DEFAULT TRUE,
    uploaded_by VARCHAR(36),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- History specific fields
    change_type ENUM('CREATED', 'UPDATED', 'DELETED') NOT NULL,
    changed_by VARCHAR(36) NOT NULL,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    change_reason TEXT,
    -- Audit fields
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(36),
    updated_by VARCHAR(36),
    version BIGINT DEFAULT 0 COMMENT 'Optimistic locking version',

    FOREIGN KEY (order_attachment_id) REFERENCES order_attachment(id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES order_data(id),
    FOREIGN KEY (document_id) REFERENCES rf_document(id),
    FOREIGN KEY (uploaded_by) REFERENCES users(id),
    FOREIGN KEY (changed_by) REFERENCES users(id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id),

    INDEX idx_order_attachment_history_attachment (order_attachment_id),
    INDEX idx_order_attachment_history_order (order_id),
    INDEX idx_order_attachment_history_document_id (document_id),
    INDEX idx_order_attachment_history_changed_at (changed_at),
    INDEX idx_order_attachment_history_change_type (change_type),
    INDEX idx_order_attachment_history_version (version_number)
) COMMENT 'History of all changes to order attachments';


-- =====================================================
-- 10. TABEL AUDIT TRAIL
-- =====================================================

-- 8.1 Comprehensive Audit Trail
-- @Entity @Table(name = "order_audit_trail")
CREATE TABLE order_audit_trail (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    order_id VARCHAR(36) NOT NULL,
    table_name VARCHAR(100) NOT NULL,
    operation_type ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    column_name VARCHAR(100),
    old_value TEXT,
    new_value TEXT,
    changed_by VARCHAR(36) NOT NULL,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(45),
    user_agent TEXT,
    
    FOREIGN KEY (order_id) REFERENCES order_data(id),
    FOREIGN KEY (changed_by) REFERENCES users(id),
    
    INDEX idx_order_audit_trail_order_id (order_id),
    INDEX idx_order_audit_trail_table_name (table_name),
    INDEX idx_order_audit_trail_operation_type (operation_type),
    INDEX idx_order_audit_trail_changed_by (changed_by),
    INDEX idx_order_audit_trail_changed_at (changed_at),
    INDEX idx_order_audit_trail_order_changed (order_id, changed_at)
) COMMENT 'Audit trail komprehensif untuk semua perubahan order';

-- =====================================================
-- 11. TABEL NOTIFICATION SYSTEM
-- =====================================================

-- 9.1 Notification Management
-- @Entity @Table(name = "order_notification")
CREATE TABLE order_notification (
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    order_id VARCHAR(36),
    user_id VARCHAR(36) NOT NULL,
    notification_type ENUM('STATUS_CHANGE', 'ASSIGNMENT', 'COMMENT', 'DEADLINE', 'APPROVAL') NOT NULL,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    read_at TIMESTAMP NULL,
    sent_via_email BOOLEAN DEFAULT FALSE,
    email_sent_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (order_id) REFERENCES order_data(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    
    INDEX idx_order_notification_user_id (user_id),
    INDEX idx_order_notification_order_id (order_id),
    INDEX idx_order_notification_type (notification_type),
    INDEX idx_order_notification_is_read (is_read),
    INDEX idx_order_notification_created_at (created_at),
    INDEX idx_order_notification_user_read (user_id, is_read)
) COMMENT 'Sistem notifikasi untuk stakeholder communication';

-- =====================================================
-- 12. CONSTRAINTS DAN VALIDATIONS
-- =====================================================

-- 10.1 Check Constraints
-- Validasi deadline tidak boleh kurang dari submission date
ALTER TABLE order_data
ADD CONSTRAINT chk_deadline_after_submission
CHECK (deadline_date IS NULL OR submission_date IS NULL OR deadline_date >= submission_date);

-- Validasi completion date tidak boleh kurang dari submission date
ALTER TABLE order_data
ADD CONSTRAINT chk_completion_after_submission
CHECK (completion_date IS NULL OR submission_date IS NULL OR completion_date >= submission_date);

-- Validasi contract value harus positif
ALTER TABLE order_data
ADD CONSTRAINT chk_contract_value_positive
CHECK (contract_value IS NULL OR contract_value >= 0);

-- Validasi sequence order harus positif
ALTER TABLE rf_order_status
ADD CONSTRAINT chk_sequence_positive
CHECK (sequence_order > 0);


-- Validasi version harus non-negative
ALTER TABLE order_data
ADD CONSTRAINT chk_version_non_negative
CHECK (version >= 0);

-- =====================================================
-- 13. SEQUENCES UNTUK AUTO-GENERATED VALUES
-- =====================================================

-- 12.1 Sequence untuk order number (jika menggunakan auto-increment)
CREATE TABLE IF NOT EXISTS order_number_sequence (
    seq_name VARCHAR(50) PRIMARY KEY,
    seq_value BIGINT NOT NULL DEFAULT 1
);

INSERT INTO order_number_sequence (seq_name, seq_value) 
VALUES ('order_number_seq', 1)
ON DUPLICATE KEY UPDATE seq_value = seq_value;

-- =====================================================
-- 14. INITIAL DATA POPULATION
-- =====================================================

-- 13.1 Data awal status order
INSERT INTO rf_order_status (code, name, description, color_code, sequence_order) VALUES
('DRAFT', 'Draft', 'Order dalam tahap persiapan', '#6B7280', 1),
('SUBMITTED', 'Submitted', 'Order telah disubmit untuk review', '#3B82F6', 2),
('REVIEW', 'Under Review', 'Order sedang dalam proses review', '#F59E0B', 3),
('APPROVED', 'Approved', 'Order telah disetujui', '#10B981', 4),
('REJECTED', 'Rejected', 'Order ditolak', '#EF4444', 5),
('IN_PROGRESS', 'In Progress', 'Order sedang diproses', '#8B5CF6', 6),
('ON_HOLD', 'On Hold', 'Order ditunda sementara', '#F59E0B', 7),
('COMPLETED', 'Completed', 'Order telah selesai', '#10B981', 8),
('CANCELLED', 'Cancelled', 'Order dibatalkan', '#EF4444', 9);

-- 13.2 Data awal priority
INSERT INTO rf_priority (code, name, description, color_code, default_deadline_days) VALUES
('LOW', 'Low', 'Prioritas rendah', '#6B7280', 14),
('NORMAL', 'Normal', 'Prioritas normal', '#3B82F6', 7),
('HIGH', 'High', 'Prioritas tinggi', '#F59E0B', 5),
('URGENT', 'Urgent', 'Prioritas mendesak', '#EF4444', 2);

-- 13.3 Default admin user (password: admin123 - harus diubah di production)
INSERT INTO users (id, username, password, email, full_name, role, department, position) VALUES
('00000000-0000-0000-0000-000000000001', 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'admin@company.com', 'System Administrator', 'ROLE_ADMIN', 'IT', 'System Administrator');

-- =====================================================
-- 15. SPRINGBOOT OPTIMIZATION NOTES
-- =====================================================

-- 14.1 JPA/Hibernate Optimization:
-- - All tables use UUID primary keys for distributed systems
-- - Added version column for optimistic locking
-- - Added created_at, updated_at for audit trails
-- - Added is_deleted for soft delete pattern
-- - Proper indexing for common query patterns
-- - Composite indexes for performance optimization
-- - All foreign keys properly defined
-- - Check constraints for data integrity

-- 14.2 Spring Security Integration:
-- - Users table includes role field for Spring Security
-- - Password field ready for BCrypt encoding
-- - Account locking fields for security
-- - Active/inactive status for user management

-- 14.3 Performance Considerations:
-- - Indexes optimized for Spring Data JPA queries
-- - Composite indexes for common filter combinations
-- - Proper data types for storage efficiency
-- - Soft delete pattern for data retention

-- Commit transaction
COMMIT;

-- =====================================================
-- SUMMARY
-- =====================================================
-- Script ini telah berhasil membuat:
-- 1. 7 tabel referensi (enhanced untuk SpringBoot)
-- 2. 1 tabel users (enhanced untuk Spring Security)
-- 3. 1 tabel workflow definition
-- 4. 1 tabel utama order_data (optimized untuk JPA)
-- 5. 3 tabel workflow & approval (order_approval, workflow_detail)
-- 6. 2 tabel status history & tracking
-- 7. 1 tabel comment & collaboration
-- 8. 2 tabel attachment management (order_attachment, order_attachment_history)
-- 9. 1 tabel audit trail
-- 10. 1 tabel notification system
-- 11. Constraints dan validations
-- 12. Sequences untuk auto-generation
-- 13. Initial data population
--
-- Total: 19 tabel dengan optimasi SpringBoot/JPA/Hibernate
-- Catatan: Ditambahkan workflow dan workflow_detail untuk state machine management
-- Perbaikan: Ditambahkan original_file_name, diperbaiki syntax error
-- Reorganisasi: Urutan tabel disesuaikan dengan dependency order untuk menghindari foreign key errors
-- Dependency Order:
-- 1. Reference tables (no dependencies): rf_order_status, rf_priority, rf_cooperation_type, rf_document_type, rf_employment_status, rf_service_cost_type, rf_document_source
-- 2. Reference tables with dependencies: rf_document (depends on rf_document_source)
-- 3. Users table
-- 4. Workflow definition (depends on users)
-- 5. Core order_data (depends on users, rf_* tables, workflow)
-- 6. Tables depending on order_data: order_approval, order_status_history, order_assignment_history, order_comment, order_attachment, order_audit_trail, order_notification
-- 7. Tables depending on workflow: workflow_detail (depends on workflow, rf_order_status)
-- 8. Tables depending on order_attachment: order_attachment_history (depends on order_attachment, order_data)
-- 9. Utility: order_number_sequence
-- =====================================================
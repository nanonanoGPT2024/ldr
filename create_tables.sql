-- Database Schema for LDR API Application
-- Generated from JPA Models in src/main/java/com/ldr/api/model/
-- Date: 2025-11-01

-- =====================================================
-- REFERENCE TABLES (RF_*)
-- =====================================================

-- Cooperation Type Reference Table
CREATE TABLE rf_cooperation_type (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);

-- Document Type Reference Table
CREATE TABLE rf_document_type (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    template_path VARCHAR(255),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);

-- Employment Status Reference Table
CREATE TABLE rf_employment_status (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);

-- Service Cost Type Reference Table
CREATE TABLE rf_service_cost_type (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);

-- Document Source Reference Table
CREATE TABLE rf_document_source (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);

-- Priority Reference Table
CREATE TABLE rf_priority (
    id VARCHAR(36) PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    color_code VARCHAR(7),
    default_deadline_days INT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);

-- Order Status Reference Table
CREATE TABLE rf_order_status (
    id VARCHAR(36) PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    color_code VARCHAR(7),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    sequence_order INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);

-- =====================================================
-- MAIN TABLES
-- =====================================================

-- Users Table
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    department VARCHAR(100),
    position VARCHAR(100),
    phone VARCHAR(20),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP,
    last_login TIMESTAMP,
    failed_login_attempts INT NOT NULL DEFAULT 0,
    locked_until TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);

-- Document Table
CREATE TABLE rf_document (
    id VARCHAR(36) PRIMARY KEY,
    document_source_id VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    deskripsi TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,

    FOREIGN KEY (document_source_id) REFERENCES rf_document_source(id)
);

-- Workflow Table
CREATE TABLE workflow (
    id VARCHAR(36) PRIMARY KEY,
    nama VARCHAR(255) NOT NULL,
    deskripsi TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);

-- Workflow Detail Table
CREATE TABLE workflow_detail (
    id VARCHAR(36) PRIMARY KEY,
    workflow_id VARCHAR(36) NOT NULL,
    current_stage VARCHAR(36) NOT NULL,
    next_stage VARCHAR(36),
    return_stage VARCHAR(36),
    reject_stage VARCHAR(36),
    sla INT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,

    FOREIGN KEY (workflow_id) REFERENCES workflow(id)
);

-- Order Data Table
CREATE TABLE order_data (
    id VARCHAR(36) PRIMARY KEY,
    order_number VARCHAR(50) UNIQUE NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    client_name VARCHAR(255) NOT NULL,
    client_trade_name VARCHAR(255),
    client_contact_person VARCHAR(255),
    client_email VARCHAR(255),
    client_phone VARCHAR(50),
    requestor_id VARCHAR(36) NOT NULL,
    requestor_name VARCHAR(255) NOT NULL,
    requestor_email VARCHAR(255),
    requestor_department VARCHAR(100),
    cooperation_type_id VARCHAR(36) NOT NULL,
    document_type_id VARCHAR(36) NOT NULL,
    cooperation_period VARCHAR(100),
    employment_status_id VARCHAR(36),
    position VARCHAR(255),
    service_cost_type_id VARCHAR(36),
    service_cost_description TEXT,
    payment_terms VARCHAR(255),
    tax_info VARCHAR(100),
    penalty_clause VARCHAR(255),
    contract_value DECIMAL(15,2),
    document_source_id VARCHAR(36),
    additional_notes TEXT,
    current_status_code VARCHAR(20) NOT NULL,
    workflow_id VARCHAR(36),
    priority_id VARCHAR(36) NOT NULL,
    assigned_to VARCHAR(36),
    current_role VARCHAR(50),
    sla INT,
    submission_date DATE,
    deadline_date DATE,
    completion_date DATE,
    version BIGINT NOT NULL DEFAULT 0,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(36),
    updated_by VARCHAR(36),

    FOREIGN KEY (requestor_id) REFERENCES users(id),
    FOREIGN KEY (cooperation_type_id) REFERENCES rf_cooperation_type(id),
    FOREIGN KEY (document_type_id) REFERENCES rf_document_type(id),
    FOREIGN KEY (employment_status_id) REFERENCES rf_employment_status(id),
    FOREIGN KEY (service_cost_type_id) REFERENCES rf_service_cost_type(id),
    FOREIGN KEY (document_source_id) REFERENCES rf_document_source(id),
    FOREIGN KEY (workflow_id) REFERENCES workflow(id),
    FOREIGN KEY (priority_id) REFERENCES rf_priority(id),
    FOREIGN KEY (assigned_to) REFERENCES users(id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);

-- Order Approval Table
CREATE TABLE order_approval (
    id VARCHAR(36) PRIMARY KEY,
    order_id VARCHAR(36) NOT NULL,
    approver_id VARCHAR(36) NOT NULL,
    approver_role VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'REQUEST',
    comments TEXT,
    approved_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,

    FOREIGN KEY (order_id) REFERENCES order_data(id),
    FOREIGN KEY (approver_id) REFERENCES users(id)
);

-- Order Assignment History Table
CREATE TABLE order_assignment_history (
    id VARCHAR(36) PRIMARY KEY,
    order_id VARCHAR(36) NOT NULL,
    assigned_to_role VARCHAR(50),
    assigned_by_role VARCHAR(50),
    assigned_by VARCHAR(36),
    completed_by VARCHAR(36),
    assignment_type VARCHAR(20) NOT NULL CHECK (assignment_type IN ('NEXT', 'RETURN', 'REJECT')),
    notes TEXT,
    sla INT,
    target_at TIMESTAMP,
    completed_at TIMESTAMP,
    assigned_at TIMESTAMP NOT NULL,

    FOREIGN KEY (order_id) REFERENCES order_data(id),
    FOREIGN KEY (assigned_by) REFERENCES users(id),
    FOREIGN KEY (completed_by) REFERENCES users(id)
);

-- Order Attachment Table
CREATE TABLE order_attachment (
    id VARCHAR(36) PRIMARY KEY,
    order_id VARCHAR(36) NOT NULL,
    document_id VARCHAR(36),
    keterangan TEXT,
    file_name VARCHAR(255) NOT NULL,
    original_file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT,
    file_type VARCHAR(100),
    mime_type VARCHAR(100),
    uploaded_by VARCHAR(36) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    FOREIGN KEY (order_id) REFERENCES order_data(id),
    FOREIGN KEY (document_id) REFERENCES rf_document(id),
    FOREIGN KEY (uploaded_by) REFERENCES users(id)
);

-- Order Attachment History Table
CREATE TABLE order_attachment_history (
    id VARCHAR(36) PRIMARY KEY,
    order_attachment_id VARCHAR(36) NOT NULL,
    order_id VARCHAR(36) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    original_file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT NOT NULL,
    mime_type VARCHAR(100) NOT NULL,
    document_id VARCHAR(36),
    keterangan TEXT,
    version_number BIGINT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    uploaded_by VARCHAR(36),
    uploaded_at TIMESTAMP,
    change_type VARCHAR(20) NOT NULL CHECK (change_type IN ('CREATED', 'UPDATED', 'DELETED')),
    changed_by VARCHAR(36) NOT NULL,
    changed_at TIMESTAMP NOT NULL,
    change_reason TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(36),
    updated_by VARCHAR(36),
    version BIGINT NOT NULL DEFAULT 0,

    FOREIGN KEY (order_attachment_id) REFERENCES order_attachment(id),
    FOREIGN KEY (order_id) REFERENCES order_data(id),
    FOREIGN KEY (document_id) REFERENCES rf_document(id),
    FOREIGN KEY (uploaded_by) REFERENCES users(id),
    FOREIGN KEY (changed_by) REFERENCES users(id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);

-- Order Status History Table
CREATE TABLE order_status_history (
    id VARCHAR(36) PRIMARY KEY,
    order_id VARCHAR(36) NOT NULL,
    from_status_code VARCHAR(20),
    to_status_code VARCHAR(20) NOT NULL,
    changed_by VARCHAR(36) NOT NULL,
    change_reason TEXT,
    changed_at TIMESTAMP NOT NULL,

    FOREIGN KEY (order_id) REFERENCES order_data(id),
    FOREIGN KEY (changed_by) REFERENCES users(id)
);

-- =====================================================
-- INDEXES FOR PERFORMANCE
-- =====================================================

-- Users table indexes
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_users_is_active ON users(is_active);

-- Order Data indexes
CREATE INDEX idx_order_data_order_number ON order_data(order_number);
CREATE INDEX idx_order_data_client_name ON order_data(client_name);
CREATE INDEX idx_order_data_requestor_id ON order_data(requestor_id);
CREATE INDEX idx_order_data_current_status_code ON order_data(current_status_code);
CREATE INDEX idx_order_data_priority_id ON order_data(priority_id);
CREATE INDEX idx_order_data_assigned_to ON order_data(assigned_to);
CREATE INDEX idx_order_data_created_at ON order_data(created_at);
CREATE INDEX idx_order_data_is_deleted ON order_data(is_deleted);

-- Order Assignment History indexes
CREATE INDEX idx_order_assignment_history_order_id ON order_assignment_history(order_id);
CREATE INDEX idx_order_assignment_history_assigned_by ON order_assignment_history(assigned_by);
CREATE INDEX idx_order_assignment_history_completed_by ON order_assignment_history(completed_by);
CREATE INDEX idx_order_assignment_history_assigned_at ON order_assignment_history(assigned_at);

-- Order Attachment indexes
CREATE INDEX idx_order_attachment_order_id ON order_attachment(order_id);
CREATE INDEX idx_order_attachment_uploaded_by ON order_attachment(uploaded_by);
CREATE INDEX idx_order_attachment_is_active ON order_attachment(is_active);
CREATE INDEX idx_order_attachment_is_deleted ON order_attachment(is_deleted);

-- Order Attachment History indexes
CREATE INDEX idx_order_attachment_history_order_attachment_id ON order_attachment_history(order_attachment_id);
CREATE INDEX idx_order_attachment_history_order_id ON order_attachment_history(order_id);
CREATE INDEX idx_order_attachment_history_changed_by ON order_attachment_history(changed_by);
CREATE INDEX idx_order_attachment_history_changed_at ON order_attachment_history(changed_at);

-- Order Status History indexes
CREATE INDEX idx_order_status_history_order_id ON order_status_history(order_id);
CREATE INDEX idx_order_status_history_changed_by ON order_status_history(changed_by);
CREATE INDEX idx_order_status_history_changed_at ON order_status_history(changed_at);

-- Order Approval indexes
CREATE INDEX idx_order_approval_order_id ON order_approval(order_id);
CREATE INDEX idx_order_approval_approver_id ON order_approval(approver_id);
CREATE INDEX idx_order_approval_status ON order_approval(status);

-- Reference table indexes
CREATE INDEX idx_rf_cooperation_type_is_active ON rf_cooperation_type(is_active);
CREATE INDEX idx_rf_document_type_is_active ON rf_document_type(is_active);
CREATE INDEX idx_rf_employment_status_is_active ON rf_employment_status(is_active);
CREATE INDEX idx_rf_service_cost_type_is_active ON rf_service_cost_type(is_active);
CREATE INDEX idx_rf_document_source_is_active ON rf_document_source(is_active);
CREATE INDEX idx_rf_priority_is_active ON rf_priority(is_active);
CREATE INDEX idx_rf_priority_code ON rf_priority(code);
CREATE INDEX idx_rf_order_status_is_active ON rf_order_status(is_active);
CREATE INDEX idx_rf_order_status_code ON rf_order_status(code);
CREATE INDEX idx_rf_order_status_sequence_order ON rf_order_status(sequence_order);

-- Workflow indexes
CREATE INDEX idx_workflow_is_active ON workflow(is_active);
CREATE INDEX idx_workflow_detail_workflow_id ON workflow_detail(workflow_id);
CREATE INDEX idx_workflow_detail_is_active ON workflow_detail(is_active);

-- Document indexes
CREATE INDEX idx_rf_document_document_source_id ON rf_document(document_source_id);
CREATE INDEX idx_rf_document_is_active ON rf_document(is_active);
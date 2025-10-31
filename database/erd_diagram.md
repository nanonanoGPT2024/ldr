# Entity Relationship Diagram (ERD)
## Order Data Management System

> **Version 3.6** - Updated on 2025-10-30
> - Added rf_document table and updated order_attachment structure
> - Changed attachment_type to document_id in order_attachment and order_attachment_history
> - Added keterangan column to order_attachment and order_attachment_history
> - Added relationships: rf_document → rf_document_source, order_attachment → rf_document
> - Enhanced document management system (20 tables total)

### Overview
ERD ini menampilkan struktur database lengkap untuk sistem Order Data Management yang dioptimasi untuk SpringBoot/JPA/Hibernate dengan 20 tabel yang terbagi dalam beberapa kelompok fungsional. Sistem workflow state machine telah ditambahkan untuk mengelola transisi status order yang fleksibel.

---

## 📊 ERD Diagram

```mermaid
erDiagram
    %% ========================================
    %% REFERENCE TABLES GROUP
    %% ========================================
    
    rf_order_status {
        varchar id PK "UUID"
        varchar code UK "Unique code"
        varchar name "Status name"
        text description
        varchar color_code "UI color code"
        boolean is_active
        int sequence_order "Workflow order"
        timestamp created_at
        timestamp updated_at
        bigint version "Optimistic lock"
    }
    
    rf_priority {
        varchar id PK "UUID"
        varchar code UK "Unique code"
        varchar name "Priority name"
        text description
        varchar color_code
        int default_deadline_days
        boolean is_active
        timestamp created_at
        timestamp updated_at
        bigint version "Optimistic lock"
    }
    
    rf_cooperation_type {
        varchar id PK "UUID"
        varchar name "Cooperation type"
        text description
        boolean is_active
        timestamp created_at
        timestamp updated_at
        bigint version "Optimistic lock"
    }
    
    rf_document_type {
        varchar id PK "UUID"
        varchar name "Document type"
        text description
        varchar template_path
        boolean is_active
        timestamp created_at
        timestamp updated_at
        bigint version "Optimistic lock"
    }
    
    rf_employment_status {
        varchar id PK "UUID"
        varchar name "Employment status"
        text description
        boolean is_active
        timestamp created_at
        timestamp updated_at
        bigint version "Optimistic lock"
    }
    
    rf_service_cost_type {
        varchar id PK "UUID"
        varchar name "Service cost type"
        text description
        boolean is_active
        timestamp created_at
        timestamp updated_at
        bigint version "Optimistic lock"
    }
    
    rf_document_source {
        varchar id PK "UUID"
        varchar name "Document source"
        text description
        boolean is_active
        timestamp created_at
        timestamp updated_at
        bigint version "Optimistic lock"
    }

    rf_document {
        varchar id PK "UUID"
        varchar name "Document name"
        text description
        varchar document_source_id FK
        boolean is_active
        timestamp created_at
        timestamp updated_at
        bigint version "Optimistic lock"
    }

    %% ========================================
    %% CORE TABLES GROUP
    %% ========================================
    
    users {
        varchar id PK "UUID"
        varchar username UK "Unique username"
        varchar password "BCrypt encoded"
        varchar email UK "Unique email"
        varchar full_name
        varchar role "Spring Security role"
        varchar department
        varchar position
        varchar phone
        boolean is_active
        boolean is_deleted "Soft delete"
        timestamp deleted_at
        timestamp last_login
        int failed_login_attempts
        timestamp locked_until
        timestamp created_at
        timestamp updated_at
        bigint version "Optimistic lock"
    }
    
    order_data {
        varchar id PK "UUID"
        varchar order_number UK "Unique order#"
        varchar title
        text description
        varchar client_name
        varchar client_trade_name
        varchar client_contact_person
        varchar client_email
        varchar client_phone
        varchar requestor_id FK "User requestor"
        varchar requestor_name
        varchar requestor_email
        varchar requestor_department
        varchar cooperation_type_id FK
        varchar document_type_id FK
        varchar cooperation_period
        varchar employment_status_id FK "Optional"
        varchar position
        varchar service_cost_type_id FK "Optional"
        text service_cost_description
        varchar payment_terms
        varchar tax_info
        varchar penalty_clause
        decimal contract_value
        varchar document_source_id FK "Optional"
        text additional_notes
        varchar current_status_id FK
        varchar workflow_id FK "Optional"
        varchar priority_id FK
        varchar assigned_to FK "Optional"
        varchar current_role VARCHAR(50) "Current role in workflow process"
        date submission_date
        date deadline_date
        date completion_date
        bigint version "Optimistic lock"
        boolean is_deleted "Soft delete"
        timestamp deleted_at
        timestamp created_at
        timestamp updated_at
        varchar created_by FK "Optional"
        varchar updated_by FK "Optional"
    }
    
    %% ========================================
    %% WORKFLOW TABLES GROUP
    %% ========================================

    workflow {
        varchar id PK "UUID"
        varchar nama "Workflow name"
        text deskripsi "Workflow description"
        boolean is_active
        timestamp created_at
        timestamp updated_at
        varchar created_by FK "Optional"
        varchar updated_by FK "Optional"
        bigint version "Optimistic lock"
    }

    workflow_detail {
        varchar id PK "UUID"
        varchar workflow_id FK
        varchar current_stage "Free text"
        varchar next_stage "Free text"
        varchar return_stage "Free text"
        varchar reject_stage "Free text"
        int sla "SLA in days"
        boolean is_active
        timestamp created_at
        timestamp updated_at
        varchar created_by FK "Optional"
        varchar updated_by FK "Optional"
        bigint version "Optimistic lock"
    }

    order_approval {
        varchar id PK "UUID"
        varchar order_id FK
        varchar approver_id FK
        varchar approver_role
        enum status "REQUEST/APPROVED/REJECTED/RETURN"
        text comments
        timestamp approved_at
        timestamp created_at
        timestamp updated_at
        bigint version "Optimistic lock"
    }
    
    %% ========================================
    %% HISTORY TABLES GROUP
    %% ========================================
    
    order_status_history {
        varchar id PK "UUID"
        varchar order_id FK
        varchar from_status_id FK "Optional"
        varchar to_status_id FK
        varchar changed_by FK
        text change_reason
        timestamp changed_at
    }
    
    order_assignment_history {
        varchar id PK "UUID"
        varchar order_id FK
        varchar assigned_to FK
        varchar assigned_by FK
        enum assignment_type "ASSIGNED/REASSIGNED/UNASSIGNED"
        text notes
        timestamp assigned_at
    }
    
    %% ========================================
    %% COLLABORATION GROUP
    %% ========================================
    
    order_comment {
        varchar id PK "UUID"
        varchar order_id FK
        varchar user_id FK
        enum comment_type "INTERNAL/EXTERNAL/SYSTEM"
        text comment_text
        varchar parent_comment_id FK "Optional self-ref"
        boolean is_edited
        timestamp edited_at
        boolean is_deleted "Soft delete"
        timestamp deleted_at
        timestamp created_at
        bigint version "Optimistic lock"
    }
    
    %% ========================================
    %% ATTACHMENT GROUP
    %% ========================================
    
    order_attachment {
        varchar id PK "UUID"
        varchar order_id FK
        varchar document_id FK "Reference to rf_document.id"
        varchar keterangan "Additional description/notes"
        varchar file_name
        varchar original_file_name "Original file name before upload"
        varchar file_path
        bigint file_size
        varchar file_type
        varchar mime_type
        varchar uploaded_by FK
        int version "Document version"
        boolean is_active
        boolean is_deleted "Soft delete"
        timestamp deleted_at
        timestamp created_at
        timestamp updated_at
    }

    order_attachment_history {
        varchar id PK "UUID"
        varchar order_attachment_id FK "Reference to order_attachment.id"
        varchar order_id FK
        varchar document_id FK "Reference to rf_document.id"
        varchar keterangan "Additional description/notes"
        varchar file_name
        varchar original_file_name
        varchar file_path
        bigint file_size
        varchar mime_type
        int version_number
        boolean is_active
        varchar uploaded_by FK "Optional"
        timestamp uploaded_at
        enum change_type "CREATED/UPDATED/DELETED"
        varchar changed_by FK
        timestamp changed_at
        text change_reason "Optional"
        timestamp created_at
        timestamp updated_at
        varchar created_by FK "Optional"
        varchar updated_by FK "Optional"
        bigint version "Optimistic lock"
    }
    
    %% ========================================
    %% AUDIT & UTILITY GROUP
    %% ========================================
    
    order_audit_trail {
        varchar id PK "UUID"
        varchar order_id FK
        varchar table_name
        enum operation_type "INSERT/UPDATE/DELETE"
        varchar column_name
        text old_value
        text new_value
        varchar changed_by FK
        timestamp changed_at
        varchar ip_address
        text user_agent
    }
    
    order_notification {
        varchar id PK "UUID"
        varchar order_id FK "Optional"
        varchar user_id FK
        enum notification_type "STATUS_CHANGE/ASSIGNMENT/COMMENT/DEADLINE/APPROVAL"
        varchar title
        text message
        boolean is_read
        timestamp read_at
        boolean sent_via_email
        timestamp email_sent_at
        timestamp created_at
    }
    
    order_number_sequence {
        varchar seq_name PK
        bigint seq_value "Auto increment value"
    }
    
    %% ========================================
    %% RELATIONSHIPS - REFERENCE TO ORDER_DATA
    %% ========================================

    rf_order_status ||--o{ order_data : "current_status"
    rf_priority ||--o{ order_data : "priority"
    rf_cooperation_type ||--o{ order_data : "cooperation_type"
    rf_document_type ||--o{ order_data : "document_type"
    rf_employment_status ||--o{ order_data : "employment_status (optional)"
    rf_service_cost_type ||--o{ order_data : "service_cost_type (optional)"
    rf_document_source ||--o{ order_data : "document_source (optional)"
    rf_document_source ||--o{ rf_document : "document_source"
    rf_document ||--o{ order_attachment : "document_id"
    rf_document ||--o{ order_attachment_history : "document_id"

    %% ========================================
    %% RELATIONSHIPS - WORKFLOW TABLES
    %% ========================================

    workflow ||--o{ order_data : "workflow (optional)"
    workflow ||--o{ workflow_detail : "has details"
    users ||--o{ workflow : "created_by (optional)"
    users ||--o{ workflow : "updated_by (optional)"
    users ||--o{ workflow_detail : "created_by (optional)"
    users ||--o{ workflow_detail : "updated_by (optional)"
    
    %% ========================================
    %% RELATIONSHIPS - USERS TO ORDER_DATA
    %% ========================================
    
    users ||--o{ order_data : "requestor"
    users ||--o{ order_data : "assigned_to (optional)"
    users ||--o{ order_data : "created_by (optional)"
    users ||--o{ order_data : "updated_by (optional)"
    
    %% ========================================
    %% RELATIONSHIPS - ORDER_DATA TO CHILD TABLES
    %% ========================================
    
    order_data ||--o{ order_approval : "has approvals"
    order_data ||--o{ order_status_history : "has status history"
    order_data ||--o{ order_assignment_history : "has assignment history"
    order_data ||--o{ order_comment : "has comments"
    order_data ||--o{ order_attachment : "has attachments"
    order_data ||--o{ order_attachment_history : "has attachment history"
    order_data ||--o{ order_audit_trail : "has audit trail"
    order_data ||--o{ order_notification : "generates notifications (optional)"
    
    %% ========================================
    %% RELATIONSHIPS - USERS TO TRACKING TABLES
    %% ========================================
    
    users ||--o{ order_approval : "approver"
    users ||--o{ order_status_history : "changed_by"
    users ||--o{ order_assignment_history : "assigned_to"
    users ||--o{ order_assignment_history : "assigned_by"
    users ||--o{ order_comment : "commenter"
    users ||--o{ order_attachment : "uploader"
    users ||--o{ order_attachment_history : "changed_by"
    users ||--o{ order_attachment_history : "uploaded_by (optional)"
    users ||--o{ order_attachment_history : "created_by (optional)"
    users ||--o{ order_attachment_history : "updated_by (optional)"
    users ||--o{ order_audit_trail : "changed_by"
    users ||--o{ order_notification : "recipient"
    
    %% ========================================
    %% RELATIONSHIPS - STATUS HISTORY
    %% ========================================
    
    rf_order_status ||--o{ order_status_history : "from_status (optional)"
    rf_order_status ||--o{ order_status_history : "to_status"
    
    %% ========================================
    %% RELATIONSHIPS - SELF-REFERENCE
    %% ========================================
    
    order_comment ||--o{ order_comment : "parent_comment (replies)"

    order_attachment ||--o{ order_attachment_history : "has history"
    order_data ||--o{ order_attachment_history : "has attachment history"
    users ||--o{ order_attachment_history : "changed_by"
```

---

## 📖 Legend & Explanation

### Kardinalitas Relasi
| Symbol | Meaning | Description |
|--------|---------|-------------|
| `\|\|--\|\|` | One to One | Setiap record di tabel A memiliki tepat satu record di tabel B |
| `\|\|--o{` | One to Many | Setiap record di tabel A dapat memiliki banyak record di tabel B |
| `}o--o{` | Many to Many | Banyak record di tabel A dapat berhubungan dengan banyak record di tabel B |
| `\|\|--o\|` | One to Zero or One | Setiap record di tabel A memiliki 0 atau 1 record di tabel B |

### Notasi Kolom
| Notation | Meaning |
|----------|---------|
| `PK` | Primary Key |
| `FK` | Foreign Key |
| `UK` | Unique Key |

---

## 🗂️ Penjelasan Kelompok Tabel

### 1️⃣ Reference Tables (8 tabel)
**Purpose:** Tabel referensi untuk data master yang jarang berubah

- **rf_order_status**: Status order dalam workflow (Draft, Submitted, Approved, dll)
- **rf_priority**: Level prioritas order (Low, Normal, High, Urgent)
- **rf_cooperation_type**: Jenis kerjasama dengan klien
- **rf_document_type**: Jenis dokumen yang diminta
- **rf_employment_status**: Status tenaga kerja
- **rf_service_cost_type**: Tipe biaya jasa
- **rf_document_source**: Sumber dokumen
- **rf_document**: Master data dokumen yang dapat di-upload sebagai attachment

**Karakteristik:**
- Semua tabel memiliki kolom audit (created_at, updated_at)
- Memiliki flag is_active untuk soft activation
- Menggunakan optimistic locking (version)
- UUID sebagai primary key

---

### 2️⃣ Core Tables (2 tabel)
**Purpose:** Tabel inti sistem untuk manajemen user dan order

#### **users**
Tabel user management yang terintegrasi dengan Spring Security
- Username dan email unique
- Password ter-enkripsi dengan BCrypt
- Role-based access control
- Account locking mechanism
- Soft delete pattern

#### **order_data**
Tabel utama yang menyimpan semua informasi order
- Order number unique dan auto-generated
- Informasi lengkap klien dan requestor
- Detail kerjasama dan financial
- Workflow dan status tracking dengan kolom `current_role`
- Timeline management
- Soft delete pattern
- **16 Foreign Keys** ke berbagai tabel referensi dan users

**Relasi Kunci:**
- Berelasi dengan SEMUA tabel referensi
- Berelasi dengan workflow (optional)
- Berelasi dengan users (4x: requestor, assigned_to, created_by, updated_by)
- Parent dari semua tabel tracking, history, dan collaboration

**Total Foreign Keys: 17 (tambah workflow_id)**

---

### 3️⃣ Workflow Tables (3 tabel)
**Purpose:** Manajemen state machine dan approval process

#### **workflow**
Definisi workflow types untuk berbagai jenis order
- Nama dan deskripsi workflow
- Status aktif/non-aktif
- Audit fields (created_by, updated_by)
- Optimistic locking

#### **workflow_detail**
State transitions untuk setiap workflow
- Mapping current_stage → next_stage (approve)
- Return_stage untuk rollback
- Reject_stage untuk rejection
- Kolom stage sekarang free text (tidak ada FK constraint)
- Kolom sla untuk Service Level Agreement dalam hari
- Unique constraint per workflow + current_stage

#### **order_approval**
Tracking real-time approval untuk setiap order
- Merekam status approval (REQUEST, APPROVED, REJECTED, RETURN)
- Menyimpan comments dari approver
- Timestamp approved_at untuk audit

**Workflow Pattern:**
```
workflow → workflow_detail (state machine definition)
order_data.workflow_id → workflow.id (assignment)
workflow_detail.stage columns (free text, no FK constraints)
```

---

### 4️⃣ History Tables (2 tabel)
**Purpose:** Audit trail untuk perubahan status dan assignment

#### **order_status_history**
Merekam setiap perubahan status order
- From status → To status
- Who changed (user reference)
- Change reason
- Timestamp changed_at

#### **order_assignment_history**
Merekam setiap perubahan assignment order
- Assignment type (ASSIGNED, REASSIGNED, UNASSIGNED)
- Who assigned to whom
- Timestamp assigned_at

**History Pattern:**
- Immutable records (tidak pernah di-update atau delete)
- Ordered by timestamp untuk chronological tracking
- Linked ke users untuk accountability

---

### 5️⃣ Collaboration (1 tabel)
**Purpose:** Kolaborasi dan komunikasi antar stakeholder

#### **order_comment**
Sistem komentar dengan fitur lengkap:
- Comment type (INTERNAL, EXTERNAL, SYSTEM)
- Support untuk threaded comments (parent_comment_id)
- Edit tracking (is_edited, edited_at)
- Soft delete pattern
- Optimistic locking

**Comment Pattern:**
- Self-referencing untuk reply/nested comments
- Internal comments hanya visible untuk internal team
- External comments visible untuk klien
- System comments untuk automated messages

---

### 6️⃣ Attachment (1 tabel)
**Purpose:** Manajemen file dan dokumen

#### **order_attachment**
Sistem attachment dengan fitur:
- Document reference (document_id ke rf_document)
- Keterangan tambahan untuk deskripsi attachment
- File metadata (name, path, size, type, mime_type, original_file_name)
- Document versioning
- Soft delete pattern
- Upload tracking (uploaded_by)

**Attachment Pattern:**
- Mendukung multiple versions dari dokumen yang sama
- Soft delete untuk menjaga history
- File metadata untuk validasi dan display
- Terintegrasi dengan master data dokumen

---

### 7️⃣ Audit & Utility (3 tabel)
**Purpose:** Audit trail komprehensif dan utility functions

#### **order_audit_trail**
Comprehensive audit trail untuk semua perubahan:
- Table name dan operation type (INSERT, UPDATE, DELETE)
- Column-level tracking (old_value, new_value)
- User tracking dengan IP address dan user agent
- Timestamp changed_at

#### **order_notification**
Sistem notifikasi untuk stakeholder communication:
- Notification type (STATUS_CHANGE, ASSIGNMENT, COMMENT, DEADLINE, APPROVAL)
- Read status tracking
- Email notification tracking
- Linked ke order (optional) dan user

#### **order_number_sequence**
Utility untuk auto-generate order number:
- Sequence name dan value
- Digunakan untuk generate unique order number

**Audit Pattern:**
- Audit trail tidak pernah di-delete
- Menyimpan perubahan di level kolom
- Tracking siapa, kapan, dari mana (IP), dan dengan apa (user agent)

---

## 🔗 Relasi Kunci (Key Relationships)

### Hub and Spoke Pattern
**order_data** sebagai hub central yang connected ke:
1. **7 Reference Tables** (One-to-Many)
2. **1 Workflow Table** (workflow)
3. **4 Users Relations** (requestor, assigned_to, created_by, updated_by)
4. **7 Child Tables** (approval, histories, comment, attachment, audit, notification)

### Multi-User Relations
**users** table berelasi dengan:
- order_data (4x different roles)
- workflow (2x: created_by, updated_by)
- workflow_detail (2x: created_by, updated_by)
- order_approval (approver tracking)
- order_status_history (change tracker)
- order_assignment_history (2x: assigned_to, assigned_by)
- order_comment (commenter)
- order_attachment (uploader)
- order_audit_trail (auditor)
- order_notification (recipient)

**Total: 16 different relationships dengan users table**

### Self-Referencing
**order_comment** memiliki self-reference untuk threaded comments:
```
order_comment (parent) ||--o{ order_comment (replies)
```

---

## 📝 Catatan Penting

### Kolom Audit Standard
Semua tabel (kecuali order_number_sequence) memiliki:
- `created_at` - Timestamp pembuatan record
- `updated_at` - Timestamp update terakhir (auto-update)
- `version` - Untuk optimistic locking (JPA/Hibernate)

### Soft Delete Pattern
Tabel dengan soft delete:
- **users** (is_deleted, deleted_at)
- **order_data** (is_deleted, deleted_at)
- **order_comment** (is_deleted, deleted_at)
- **order_attachment** (is_deleted, deleted_at)

**Why Soft Delete?**
- Menjaga data integrity dan history
- Memungkinkan recovery/restore
- Audit trail requirement
- Legal compliance

### Optimistic Locking
Semua tabel (kecuali history dan sequence) memiliki kolom `version`:
- Mencegah concurrent update conflicts
- Terintegrasi dengan JPA/Hibernate @Version
- Auto-increment pada setiap update

### Data Integrity
- **17 Check Constraints** untuk validasi data
- Deadline harus >= submission date
- Completion date harus >= submission date
- Contract value harus >= 0
- Sequence dan approval level harus > 0
- Version harus >= 0

---

## 🎯 Query Patterns Common

### Dashboard Queries
```sql
-- Active orders by status
SELECT * FROM order_data 
WHERE is_deleted = FALSE 
AND current_status_id = ?

-- Orders by assigned user
SELECT * FROM order_data 
WHERE assigned_to = ? 
AND is_deleted = FALSE

-- Urgent orders approaching deadline
SELECT * FROM order_data 
WHERE priority_id = 'URGENT' 
AND deadline_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 2 DAY)
```

### History Tracking
```sql
-- Order timeline
SELECT * FROM order_status_history 
WHERE order_id = ? 
ORDER BY changed_at DESC

-- Assignment history
SELECT * FROM order_assignment_history 
WHERE order_id = ? 
ORDER BY assigned_at DESC
```

### Collaboration
```sql
-- All comments for order
SELECT * FROM order_comment 
WHERE order_id = ? 
AND is_deleted = FALSE 
ORDER BY created_at DESC

-- Unread notifications
SELECT * FROM order_notification 
WHERE user_id = ? 
AND is_read = FALSE 
ORDER BY created_at DESC
```

---

## 🚀 SpringBoot Integration Notes

### JPA Entity Annotations
```java
@Entity
@Table(name = "order_data")
public class OrderData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Version
    private Long version; // Optimistic locking
    
    @ManyToOne
    @JoinColumn(name = "current_status_id")
    private OrderStatus currentStatus;
    
    // ... other fields
}
```

### Spring Security
```java
@Entity
@Table(name = "users")
public class User implements UserDetails {
    private String username;
    private String password; // BCrypt encoded
    private String role; // ROLE_ADMIN, ROLE_USER, etc.
    private Boolean isActive;
    // ... implements UserDetails methods
}
```

### Soft Delete
```java
@Entity
@Where(clause = "is_deleted = false") // Hibernate @Where
@SQLDelete(sql = "UPDATE order_data SET is_deleted = true, deleted_at = NOW() WHERE id = ?")
public class OrderData {
    // ... fields
}
```

---

## 📊 Statistics

| Category | Count | Tables |
|----------|-------|--------|
| Reference Tables | 8 | rf_order_status, rf_priority, rf_cooperation_type, rf_document_type, rf_employment_status, rf_service_cost_type, rf_document_source, rf_document |
| Core Tables | 2 | users, order_data |
| Workflow Tables | 3 | workflow, workflow_detail, order_approval |
| History Tables | 2 | order_status_history, order_assignment_history |
| Collaboration | 1 | order_comment |
| Attachment | 1 | order_attachment |
| Audit & Utility | 3 | order_audit_trail, order_notification, order_number_sequence |
| **TOTAL** | **20** | All tables |

### Relationship Statistics
- **Foreign Keys**: 57 total foreign key constraints
- **Indexes**: 100+ indexes untuk query optimization
- **Check Constraints**: 17 constraints untuk data validation
- **Unique Constraints**: 6 unique constraints (username, email, order_number, codes, workflow_detail unique)

---

## 🔍 Complex Relationships Explained

### Order Complete Lifecycle

```
1. WORKFLOW SETUP
   workflow (selected) → order_data.workflow_id
   workflow_detail (state machine) defined

2. ORDER CREATION
   users (requestor) → order_data (DRAFT status)

3. APPROVAL SETUP
   order_approval (PENDING) created based on approval level

4. APPROVAL PROCESS
   users (approver) → order_approval (APPROVED/REJECTED)
   ↓
   workflow_detail (determine next_stage/return_stage/reject_stage)
   ↓
   order_status_history (track status change)

5. ASSIGNMENT
   users (admin) → order_data (assigned_to)
   ↓
   order_assignment_history (track assignment)

6. COLLABORATION
   users → order_comment (discussions)
   users → order_attachment (upload files)

7. NOTIFICATIONS
   order_data changes → order_notification (alert users)

8. AUDIT
   All changes → order_audit_trail (complete audit)

9. COMPLETION
   order_data (COMPLETED status)
   ↓
   order_status_history (final status)
```

### Approval Workflow Chain

```
order_data (submitted, workflow_id assigned)
  ↓
workflow_detail (lookup current_stage=DRAFT)
  ↓
order_approval (Level 1, PENDING, Manager role)
  ↓
users (Manager approves)
  ↓
order_approval (Level 1, APPROVED)
  ↓
workflow_detail (next_stage=REVIEW)
  ↓
order_data (status = REVIEW)
  ↓
order_status_history (DRAFT → REVIEW)
  ↓
order_approval (Level 2, PENDING, Director role)
  ↓
users (Director approves)
  ↓
workflow_detail (next_stage=APPROVED)
  ↓
order_data (status = APPROVED)
  ↓
order_status_history (REVIEW → APPROVED)
```

---

## 🔄 Workflow Integration

### Bagaimana Workflow Mengatur Order Status Transitions

Workflow system menggantikan hard-coded approval logic dengan state machine yang fleksibel:

#### **State Machine Pattern**
```
Current Status → Action → Next Status
     ↓            ↓          ↓
workflow_detail  (approve)  next_stage
(current_stage)  (reject)   reject_stage
                 (return)   return_stage
```

#### **Contoh Workflow Procurement**
```
1. DRAFT → SUBMIT → REVIEW (Manager approval)
2. REVIEW → APPROVE → APPROVED (Director approval)
3. REVIEW → REJECT → REJECTED (Final rejection)
4. REVIEW → RETURN → DRAFT (Return to requester)
5. APPROVED → ASSIGN → IN_PROGRESS (Assignment)
6. IN_PROGRESS → COMPLETE → COMPLETED (Final completion)
```

#### **Integration dengan Existing Approval System**
- **order_approval** tetap digunakan untuk tracking approval levels
- **workflow_detail** menentukan status transitions berdasarkan approval outcome
- **order_status_history** merekam semua perubahan status
- **order_data.workflow_id** menentukan workflow yang digunakan

#### **Benefits**
- ✅ **Flexible**: Workflow dapat diubah tanpa code changes
- ✅ **Auditable**: Semua transitions tercatat
- ✅ **Configurable**: Berbeda workflow untuk berbeda jenis order
- ✅ **Maintainable**: State machine logic terpusat

---

## 💡 Best Practices Implementation

### 1. Separation of Concerns
- Reference data terpisah dari transactional data
- History tables terpisah untuk audit
- Approval tracking directly linked to orders

### 2. Data Integrity
- Foreign key constraints enforce referential integrity
- Check constraints ensure business rules
- Unique constraints prevent duplicates

### 3. Performance Optimization
- Strategic indexing untuk common queries
- Composite indexes untuk filter combinations
- Proper data types untuk storage efficiency

### 4. Soft Delete Strategy
- Maintain data history
- Enable recovery/restore
- Comply with audit requirements

### 5. Optimistic Locking
- Prevent concurrent update conflicts
- Retry mechanism untuk conflict resolution
- Version tracking untuk audit

---

## 🎓 Conclusion

Database ini dirancang dengan prinsip:
- ✅ **Scalability**: UUID keys untuk distributed systems
- ✅ **Maintainability**: Clear separation of concerns
- ✅ **Auditability**: Comprehensive audit trails
- ✅ **Security**: Spring Security integration
- ✅ **Performance**: Optimized indexing strategy
- ✅ **Flexibility**: Multi-level approval system
- ✅ **Reliability**: Data integrity constraints
- ✅ **Recoverability**: Soft delete pattern

Total **20 tabel** yang bekerja harmonis untuk mendukung complete Order Data Management System dengan SpringBoot/JPA/Hibernate dan workflow state machine.

---

**Generated by:** Kilo Code
**Date:** 2025-10-30
**Schema Version:** 3.6
**Database:** MySQL/MariaDB
**Framework:** SpringBoot with JPA/Hibernate
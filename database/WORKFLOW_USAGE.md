# WORKFLOW USAGE GUIDE

## 📋 Overview

Sistem workflow ini dirancang untuk mengelola state transitions pada order data dengan cara yang fleksibel dan dapat dikustomisasi. Sistem ini terdiri dari dua tabel utama: `workflow` dan `workflow_detail`.

## 🏗️ Struktur Tabel

### Tabel `workflow`
Menyimpan definisi workflow yang tersedia dalam sistem.

| Kolom | Tipe | Deskripsi |
|-------|------|-----------|
| `id` | VARCHAR(36) | Primary key (UUID) |
| `nama` | VARCHAR(255) | Nama workflow |
| `deskripsi` | TEXT | Deskripsi workflow |
| `is_active` | BOOLEAN | Status aktif/non-aktif |
| `created_at` | TIMESTAMP | Waktu pembuatan |
| `updated_at` | TIMESTAMP | Waktu update terakhir |
| `created_by` | VARCHAR(36) | FK ke users.id |
| `updated_by` | VARCHAR(36) | FK ke users.id |
| `version` | BIGINT | Optimistic locking |

### Tabel `workflow_detail`
Menyimpan detail transisi state untuk setiap workflow.

| Kolom | Tipe | Deskripsi |
|-------|------|-----------|
| `id` | VARCHAR(36) | Primary key (UUID) |
| `workflow_id` | VARCHAR(36) | FK ke workflow.id |
| `current_stage` | VARCHAR(100) | Stage saat ini (free text) |
| `next_stage` | VARCHAR(100) | Stage berikutnya jika approve (free text) |
| `return_stage` | VARCHAR(100) | Stage untuk return/rollback (free text) |
| `reject_stage` | VARCHAR(100) | Stage untuk reject (free text) |
| `sla` | VARCHAR(50) | SLA untuk transisi ini (format: 2h, 1d, 30m, etc.) |
| `is_active` | BOOLEAN | Status aktif/non-aktif |
| `created_at` | TIMESTAMP | Waktu pembuatan |
| `updated_at` | TIMESTAMP | Waktu update terakhir |
| `created_by` | VARCHAR(36) | FK ke users.id |
| `updated_by` | VARCHAR(36) | FK ke users.id |
| `version` | BIGINT | Optimistic locking |

### Tabel `order_approval`
Menyimpan tracking approval untuk setiap order dengan multi-level approval support.

| Kolom | Tipe | Deskripsi |
|-------|------|-----------|
| `id` | VARCHAR(36) | Primary key (UUID) |
| `order_id` | VARCHAR(36) | FK ke order_data.id |
| `approver_id` | VARCHAR(36) | FK ke users.id |
| `approver_role` | VARCHAR(50) | Role approver |
| `status` | ENUM | Status approval (REQUEST, APPROVED, REJECTED, RETURN) |
| `comments` | TEXT | Komentar approval |
| `approved_at` | TIMESTAMP | Waktu approval |
| `created_at` | TIMESTAMP | Waktu pembuatan |
| `updated_at` | TIMESTAMP | Waktu update terakhir |
| `version` | BIGINT | Optimistic locking |

## 📚 REFERENCE TABLES

### Tabel `rf_document`
Tabel referensi dokumen yang menyimpan master data dokumen dengan sumber dokumen.

| Kolom | Tipe | Deskripsi |
|-------|------|-----------|
| `id` | VARCHAR(36) | Primary key (UUID) |
| `document_source_id` | VARCHAR(36) | FK ke rf_document_source.id |
| `name` | VARCHAR(255) | Nama dokumen |
| `deskripsi` | TEXT | Deskripsi dokumen |
| `is_active` | BOOLEAN | Status aktif/non-aktif |
| `created_at` | TIMESTAMP | Waktu pembuatan |
| `updated_at` | TIMESTAMP | Waktu update terakhir |
| `created_by` | VARCHAR(36) | FK ke users.id |
| `updated_by` | VARCHAR(36) | FK ke users.id |
| `version` | BIGINT | Optimistic locking |

**Total tabel referensi: 8 tabel**

## 📊 Contoh Data Workflow Procurement

### 1. Buat Workflow
```sql
INSERT INTO workflow (id, nama, deskripsi, created_by) VALUES
('wf-proc-001', 'Procurement Standard', 'Workflow standar untuk procurement order', '00000000-0000-0000-0000-000000000001');
```

### 2. Definisikan State Transitions
```sql
-- DRAFT -> SUBMITTED (submit)
INSERT INTO workflow_detail (workflow_id, current_stage, next_stage, created_by) VALUES
('wf-proc-001', (SELECT id FROM rf_order_status WHERE code = 'DRAFT'), (SELECT id FROM rf_order_status WHERE code = 'SUBMITTED'), '00000000-0000-0000-0000-000000000001');

-- SUBMITTED -> REVIEW (review)
INSERT INTO workflow_detail (workflow_id, current_stage, next_stage, return_stage, reject_stage, created_by) VALUES
('wf-proc-001', (SELECT id FROM rf_order_status WHERE code = 'SUBMITTED'), (SELECT id FROM rf_order_status WHERE code = 'REVIEW'), (SELECT id FROM rf_order_status WHERE code = 'DRAFT'), (SELECT id FROM rf_order_status WHERE code = 'REJECTED'), '00000000-0000-0000-0000-000000000001');

-- REVIEW -> APPROVED (approve) atau REJECTED (reject)
INSERT INTO workflow_detail (workflow_id, current_stage, next_stage, return_stage, reject_stage, created_by) VALUES
('wf-proc-001', (SELECT id FROM rf_order_status WHERE code = 'REVIEW'), (SELECT id FROM rf_order_status WHERE code = 'APPROVED'), (SELECT id FROM rf_order_status WHERE code = 'SUBMITTED'), (SELECT id FROM rf_order_status WHERE code = 'REJECTED'), '00000000-0000-0000-0000-000000000001');

-- APPROVED -> IN_PROGRESS (start processing)
INSERT INTO workflow_detail (workflow_id, current_stage, next_stage, created_by) VALUES
('wf-proc-001', (SELECT id FROM rf_order_status WHERE code = 'APPROVED'), (SELECT id FROM rf_order_status WHERE code = 'IN_PROGRESS'), '00000000-0000-0000-0000-000000000001');

-- IN_PROGRESS -> COMPLETED (finish) atau ON_HOLD (pause)
INSERT INTO workflow_detail (workflow_id, current_stage, next_stage, return_stage, created_by) VALUES
('wf-proc-001', (SELECT id FROM rf_order_status WHERE code = 'IN_PROGRESS'), (SELECT id FROM rf_order_status WHERE code = 'COMPLETED'), (SELECT id FROM rf_order_status WHERE code = 'ON_HOLD'), '00000000-0000-0000-0000-000000000001');

-- ON_HOLD -> IN_PROGRESS (resume)
INSERT INTO workflow_detail (workflow_id, current_stage, next_stage, created_by) VALUES
('wf-proc-001', (SELECT id FROM rf_order_status WHERE code = 'ON_HOLD'), (SELECT id FROM rf_order_status WHERE code = 'IN_PROGRESS'), '00000000-0000-0000-0000-000000000001');
```

## 🔍 Query untuk Mendapatkan Next Stage

### Get Next Stage untuk Approve
```sql
SELECT
    ros_next.code as next_stage_code,
    ros_next.name as next_stage_name
FROM workflow_detail wd
JOIN rf_order_status ros_current ON wd.current_stage = ros_current.id
LEFT JOIN rf_order_status ros_next ON wd.next_stage = ros_next.id
WHERE wd.workflow_id = :workflow_id
  AND wd.current_stage = :current_status_id
  AND wd.is_active = TRUE;
```

### Get Return Stage untuk Rollback
```sql
SELECT
    ros_return.code as return_stage_code,
    ros_return.name as return_stage_name
FROM workflow_detail wd
JOIN rf_order_status ros_current ON wd.current_stage = ros_current.id
LEFT JOIN rf_order_status ros_return ON wd.return_stage = ros_return.id
WHERE wd.workflow_id = :workflow_id
  AND wd.current_stage = :current_status_id
  AND wd.is_active = TRUE;
```

### Get Reject Stage
```sql
SELECT
    ros_reject.code as reject_stage_code,
    ros_reject.name as reject_stage_name
FROM workflow_detail wd
JOIN rf_order_status ros_current ON wd.current_stage = ros_current.id
LEFT JOIN rf_order_status ros_reject ON wd.reject_stage = ros_reject.id
WHERE wd.workflow_id = :workflow_id
  AND wd.current_stage = :current_status_id
  AND wd.is_active = TRUE;
```

### Get All Possible Transitions dari Current Stage
```sql
SELECT
    'APPROVE' as action_type,
    ros_next.code as target_stage_code,
    ros_next.name as target_stage_name,
    ros_next.sequence_order
FROM workflow_detail wd
LEFT JOIN rf_order_status ros_next ON wd.next_stage = ros_next.id
WHERE wd.workflow_id = :workflow_id
  AND wd.current_stage = :current_status_id
  AND wd.next_stage IS NOT NULL
  AND wd.is_active = TRUE

UNION ALL

SELECT
    'RETURN' as action_type,
    ros_return.code as target_stage_code,
    ros_return.name as target_stage_name,
    ros_return.sequence_order
FROM workflow_detail wd
LEFT JOIN rf_order_status ros_return ON wd.return_stage = ros_return.id
WHERE wd.workflow_id = :workflow_id
  AND wd.current_stage = :current_status_id
  AND wd.return_stage IS NOT NULL
  AND wd.is_active = TRUE

UNION ALL

SELECT
    'REJECT' as action_type,
    ros_reject.code as target_stage_code,
    ros_reject.name as target_stage_name,
    ros_reject.sequence_order
FROM workflow_detail wd
LEFT JOIN rf_order_status ros_reject ON wd.reject_stage = ros_reject.id
WHERE wd.workflow_id = :workflow_id
  AND wd.current_stage = :current_status_id
  AND wd.reject_stage IS NOT NULL
  AND wd.is_active = TRUE

ORDER BY sequence_order;
```

## 🔗 Integration dengan Order Data

### Assign Workflow ke Order
```sql
UPDATE order_data
SET workflow_id = 'wf-proc-001'
WHERE id = :order_id;
```

### Get Workflow Info untuk Order
```sql
SELECT
    od.*,
    w.nama as workflow_name,
    w.deskripsi as workflow_description,
    ros_current.code as current_status_code,
    ros_current.name as current_status_name
FROM order_data od
LEFT JOIN workflow w ON od.workflow_id = w.id
LEFT JOIN rf_order_status ros_current ON od.current_status_id = ros_current.id
WHERE od.id = :order_id;
```

## 📝 Best Practices

### 1. **Workflow Design**
- Setiap workflow harus memiliki alur yang logis
- Pastikan tidak ada circular dependencies
- Gunakan return_stage untuk rollback yang masuk akal
- reject_stage biasanya mengarah ke status terminal

### 2. **Data Integrity**
- Selalu gunakan UUID untuk primary keys
- Pastikan semua FK references valid
- Gunakan UNIQUE constraint pada (workflow_id, current_stage)
- Soft delete dengan is_active flag

### 3. **Performance**
- Index pada kolom yang sering di-query
- Gunakan composite indexes untuk query kombinasi
- Optimalkan query dengan JOIN yang tepat

### 4. **Audit Trail**
- Selalu set created_by dan updated_by
- Gunakan version untuk optimistic locking
- Log semua perubahan status di order_status_history

### 5. **Flexibility**
- Workflow dapat diaktifkan/non-aktifkan tanpa menghapus data
- Satu workflow dapat digunakan oleh multiple orders
- Mudah menambah workflow baru tanpa mengubah kode

## 🚀 Spring Boot Integration

### Entity Mapping
```java
@Entity
@Table(name = "workflow")
public class Workflow {
    @Id
    private String id;

    private String nama;
    private String deskripsi;

    @Column(name = "is_active")
    private Boolean active;

    // Audit fields
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @Version
    private Long version;

    // Relations
    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL)
    private List<WorkflowDetail> details;
}

@Entity
@Table(name = "workflow_detail")
public class WorkflowDetail {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "workflow_id")
    private Workflow workflow;

    @Column(name = "current_stage")
    private String currentStage; // Free text stage

    @Column(name = "next_stage")
    private String nextStage; // Free text stage

    @Column(name = "return_stage")
    private String returnStage; // Free text stage

    @Column(name = "reject_stage")
    private String rejectStage; // Free text stage

    private String sla; // SLA duration (e.g., "2h", "1d", "30m")

    // ... audit fields
}

@Entity
@Table(name = "order_data")
public class OrderData {
    @Id
    private String id;

    // ... other fields

    @Column(name = "current_role")
    private String currentRole;

    // Relations
    @ManyToOne
    @JoinColumn(name = "workflow_id")
    private Workflow workflow;

    @ManyToOne
    @JoinColumn(name = "current_status_id")
    private OrderStatus currentStatus;

    // ... audit fields
}

@Entity
@Table(name = "order_approval")
public class OrderApproval {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderData order;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private User approver;

    private String approverRole;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    private String comments;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    // Audit fields
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    public enum ApprovalStatus {
        REQUEST, APPROVED, REJECTED, RETURN
    }
}

@Entity
@Table(name = "order_attachment")
public class OrderAttachment {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderData order;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private RfDocument document;

    @Column(name = "keterangan")
    private String keterangan;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "mime_type")
    private String mimeType;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;

    @Column(name = "version")
    private Integer version;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "is_deleted")
    private Boolean deleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Audit fields
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @Version
    private Long versionLock;
}

@Entity
@Table(name = "order_attachment_history")
public class OrderAttachmentHistory {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "order_attachment_id")
    private OrderAttachment attachment;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderData order;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "mime_type")
    private String mimeType;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private RfDocument document;

    @Column(name = "keterangan")
    private String keterangan;

    @Column(name = "version_number")
    private Integer versionNumber;

    @Column(name = "is_active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @Enumerated(EnumType.STRING)
    private ChangeType changeType;

    @ManyToOne
    @JoinColumn(name = "changed_by")
    private User changedBy;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @Column(name = "change_reason")
    private String changeReason;

    // Audit fields
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @Version
    private Long version;

    public enum ChangeType {
        CREATED, UPDATED, DELETED
    }
}
```

### Service Layer
```java
@Service
public class WorkflowService {

    public OrderStatus getNextStage(String workflowId, String currentStatusId, String action) {
        // Logic untuk mendapatkan next stage berdasarkan action
    }

    public List<WorkflowTransition> getAvailableTransitions(String workflowId, String currentStatusId) {
        // Logic untuk mendapatkan semua transisi yang tersedia
    }

    public boolean isValidTransition(String workflowId, String fromStatusId, String toStatusId) {
        // Validation logic
    }
}

@Service
public class ApprovalService {

    public OrderApproval createApprovalRequest(OrderData order, User approver, String role) {
        OrderApproval approval = new OrderApproval();
        approval.setOrder(order);
        approval.setApprover(approver);
        approval.setApproverRole(role);
        approval.setStatus(OrderApproval.ApprovalStatus.REQUEST);
        return approvalRepository.save(approval);
    }

    public OrderApproval approveOrder(OrderApproval approval, String comments) {
        approval.setStatus(OrderApproval.ApprovalStatus.APPROVED);
        approval.setComments(comments);
        approval.setApprovedAt(LocalDateTime.now());
        return approvalRepository.save(approval);
    }

    public List<OrderApproval> getPendingApprovalsForUser(String userId) {
        return approvalRepository.findByApproverIdAndStatus(userId, OrderApproval.ApprovalStatus.REQUEST);
    }

    public boolean isOrderFullyApproved(String orderId, int requiredLevels) {
        List<OrderApproval> approvals = approvalRepository.findByOrderIdAndStatus(orderId, OrderApproval.ApprovalStatus.APPROVED);
        return approvals.size() >= requiredLevels;
    }
}
```

## 🔧 Maintenance

### Menambah Workflow Baru
1. Insert ke tabel `workflow`
2. Insert detail transitions ke `workflow_detail`
3. Test dengan data sample
4. Assign ke order yang relevan

### Mengubah Workflow Existing
1. Set is_active = FALSE untuk transitions yang tidak berlaku
2. Insert transitions baru dengan is_active = TRUE
3. Update orders yang menggunakan workflow tersebut
4. Test thoroughly sebelum production

### Monitoring
- Monitor usage per workflow
- Track error rates pada transitions
- Audit log untuk perubahan workflow
- Performance monitoring pada queries

---

---

## 📎 ATTACHMENT TABLES

### Tabel `order_attachment`
Tabel utama untuk menyimpan attachment files dengan referensi ke dokumen master.

| Kolom | Tipe | Deskripsi |
|-------|------|-----------|
| `id` | VARCHAR(36) | Primary key (UUID) |
| `order_id` | VARCHAR(36) | FK ke order_data.id |
| `document_id` | VARCHAR(36) | FK ke rf_document.id |
| `keterangan` | TEXT | Keterangan tambahan untuk attachment |
| `file_name` | VARCHAR(255) | Nama file yang disimpan |
| `original_file_name` | VARCHAR(255) | Nama file asli dari user |
| `file_path` | VARCHAR(500) | Path lengkap file |
| `file_size` | BIGINT | Ukuran file dalam bytes |
| `file_type` | VARCHAR(100) | Tipe file |
| `mime_type` | VARCHAR(100) | MIME type file |
| `uploaded_by` | VARCHAR(36) | FK ke users.id |
| `version` | INT | Nomor versi file |
| `is_active` | BOOLEAN | Status aktif file |
| `is_deleted` | BOOLEAN | Soft delete flag |
| `deleted_at` | TIMESTAMP | Waktu penghapusan |
| `created_at` | TIMESTAMP | Waktu pembuatan |
| `updated_at` | TIMESTAMP | Waktu update terakhir |

### Tabel `order_attachment_history`
Tabel history untuk tracking semua perubahan pada attachment files dengan kolom `document_id` dan `keterangan`.

| Kolom | Tipe | Deskripsi |
|-------|------|-----------|
| `id` | VARCHAR(36) | Primary key (UUID) |
| `order_attachment_id` | VARCHAR(36) | FK ke order_attachment.id |
| `order_id` | VARCHAR(36) | FK ke order_data.id |
| `file_name` | VARCHAR(255) | Nama file saat perubahan |
| `original_file_name` | VARCHAR(255) | Nama file asli dari user |
| `file_path` | VARCHAR(500) | Path file saat perubahan |
| `file_size` | BIGINT | Ukuran file dalam bytes |
| `mime_type` | VARCHAR(100) | MIME type file |
| `document_id` | VARCHAR(36) | FK ke rf_document.id |
| `keterangan` | TEXT | Keterangan tambahan saat perubahan |
| `version_number` | INT | Nomor versi file |
| `is_active` | BOOLEAN | Status aktif file saat perubahan |
| `uploaded_by` | VARCHAR(36) | FK ke users.id (optional) |
| `uploaded_at` | TIMESTAMP | Waktu upload asli |
| `change_type` | ENUM | Tipe perubahan (CREATED, UPDATED, DELETED) |
| `changed_by` | VARCHAR(36) | FK ke users.id - siapa yang membuat perubahan |
| `changed_at` | TIMESTAMP | Waktu perubahan terjadi |
| `change_reason` | TEXT | Alasan perubahan (optional) |
| `created_at` | TIMESTAMP | Waktu record history dibuat |
| `updated_at` | TIMESTAMP | Waktu update record history |
| `created_by` | VARCHAR(36) | FK ke users.id (optional) |
| `updated_by` | VARCHAR(36) | FK ke users.id (optional) |
| `version` | BIGINT | Optimistic locking |

**Catatan**: Kolom `document_id` adalah foreign key ke tabel `rf_document` untuk referensi master dokumen, dan kolom `keterangan` menyimpan deskripsi tambahan untuk attachment.

### Cara Penggunaan

#### 1. **Mencatat Pembuatan Attachment Baru**
```sql
INSERT INTO order_attachment_history (
    id, order_attachment_id, order_id, file_name, original_file_name,
    file_path, file_size, mime_type, attachment_type, version_number,
    is_active, uploaded_by, uploaded_at, change_type, changed_by,
    changed_at, change_reason
) VALUES (
    UUID(), :attachment_id, :order_id, :file_name, :original_file_name,
    :file_path, :file_size, :mime_type, :attachment_type, 1,
    TRUE, :uploaded_by, NOW(), 'CREATED', :changed_by,
    NOW(), 'Initial upload'
);
```

#### 2. **Mencatat Update Attachment**
```sql
INSERT INTO order_attachment_history (
    id, order_attachment_id, order_id, file_name, original_file_name,
    file_path, file_size, mime_type, attachment_type, version_number,
    is_active, uploaded_by, uploaded_at, change_type, changed_by,
    changed_at, change_reason
) VALUES (
    UUID(), :attachment_id, :order_id, :new_file_name, :new_original_file_name,
    :new_file_path, :new_file_size, :new_mime_type, :attachment_type, :new_version,
    TRUE, :uploaded_by, :original_uploaded_at, 'UPDATED', :changed_by,
    NOW(), :change_reason
);
```

#### 3. **Mencatat Penghapusan Attachment**
```sql
INSERT INTO order_attachment_history (
    id, order_attachment_id, order_id, file_name, original_file_name,
    file_path, file_size, mime_type, attachment_type, version_number,
    is_active, uploaded_by, uploaded_at, change_type, changed_by,
    changed_at, change_reason
) VALUES (
    UUID(), :attachment_id, :order_id, :file_name, :original_file_name,
    :file_path, :file_size, :mime_type, :attachment_type, :version_number,
    FALSE, :uploaded_by, :uploaded_at, 'DELETED', :changed_by,
    NOW(), :delete_reason
);
```

### Query Patterns

#### **Melihat History Lengkap Attachment**
```sql
SELECT
    hah.*,
    u_changed.full_name as changed_by_name,
    u_uploaded.full_name as uploaded_by_name,
    ros.code as order_status_code
FROM order_attachment_history hah
LEFT JOIN users u_changed ON hah.changed_by = u_changed.id
LEFT JOIN users u_uploaded ON hah.uploaded_by = u_uploaded.id
LEFT JOIN order_data od ON hah.order_id = od.id
LEFT JOIN rf_order_status ros ON od.current_status_id = ros.id
WHERE hah.order_attachment_id = :attachment_id
ORDER BY hah.changed_at DESC;
```

#### **Melihat Perubahan Terakhir untuk Attachment**
```sql
SELECT * FROM order_attachment_history
WHERE order_attachment_id = :attachment_id
ORDER BY changed_at DESC
LIMIT 1;
```

#### **Melihat Semua Attachment History untuk Order**
```sql
SELECT
    hah.*,
    oa.file_name as current_file_name,
    u_changed.full_name as changed_by_name
FROM order_attachment_history hah
LEFT JOIN order_attachment oa ON hah.order_attachment_id = oa.id
LEFT JOIN users u_changed ON hah.changed_by = u_changed.id
WHERE hah.order_id = :order_id
ORDER BY hah.changed_at DESC;
```

#### **Statistik Perubahan Attachment**
```sql
SELECT
    change_type,
    COUNT(*) as total_changes,
    COUNT(DISTINCT order_attachment_id) as affected_attachments
FROM order_attachment_history
WHERE changed_at >= :start_date AND changed_at <= :end_date
GROUP BY change_type;
```

#### **Attachment yang Paling Sering Diubah**
```sql
SELECT
    order_attachment_id,
    COUNT(*) as change_count,
    MAX(changed_at) as last_changed,
    GROUP_CONCAT(DISTINCT change_type) as change_types
FROM order_attachment_history
GROUP BY order_attachment_id
ORDER BY change_count DESC;
```

### Best Practices

#### **1. Data Integrity**
- Selalu set `order_attachment_id` dan `order_id` dengan benar
- Pastikan `changed_by` tidak null untuk audit trail
- Gunakan `change_reason` untuk menjelaskan alasan perubahan

#### **2. Performance**
- Index pada `order_attachment_id`, `order_id`, `changed_at`, `change_type`
- Query dengan filter tanggal untuk performance
- Archive old history jika diperlukan

#### **3. Audit Trail**
- Setiap perubahan attachment harus tercatat
- Include context perubahan di `change_reason`
- Track user yang melakukan perubahan

#### **4. Versioning**
- Increment `version_number` pada setiap update
- Simpan snapshot lengkap dari attachment state
- Enable rollback ke versi sebelumnya

### Spring Boot Integration

#### **Entity Mapping**
```java
@Entity
@Table(name = "order_attachment_history")
public class OrderAttachmentHistory {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "order_attachment_id")
    private OrderAttachment attachment;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderData order;

    @Enumerated(EnumType.STRING)
    private ChangeType changeType;

    @ManyToOne
    @JoinColumn(name = "changed_by")
    private User changedBy;

    private LocalDateTime changedAt;
    private String changeReason;

    // ... other fields
}

public enum ChangeType {
    CREATED, UPDATED, DELETED
}
```

#### **Service Layer**
```java
@Service
public class AttachmentHistoryService {

    public void recordAttachmentChange(OrderAttachment attachment, ChangeType changeType, String changedBy, String reason) {
        OrderAttachmentHistory history = new OrderAttachmentHistory();
        history.setAttachment(attachment);
        history.setOrder(attachment.getOrder());
        history.setChangeType(changeType);
        history.setChangedBy(userRepository.findById(changedBy).orElse(null));
        history.setChangedAt(LocalDateTime.now());
        history.setChangeReason(reason);

        // Copy current attachment state
        history.setFileName(attachment.getFileName());
        history.setOriginalFileName(attachment.getOriginalFileName());
        history.setFilePath(attachment.getFilePath());
        history.setFileSize(attachment.getFileSize());
        history.setMimeType(attachment.getMimeType());
        history.setAttachmentType(attachment.getAttachmentType());
        history.setVersionNumber(attachment.getVersion());
        history.setIsActive(attachment.getIsActive());
        history.setUploadedBy(attachment.getUploadedBy());
        history.setUploadedAt(attachment.getUploadedAt());

        historyRepository.save(history);
    }
}
```

### Maintenance

#### **Monitoring**
- Track jumlah perubahan per attachment
- Monitor storage growth dari history table
- Alert jika ada perubahan suspicious

#### **Cleanup**
- Archive history yang sangat lama jika diperlukan
- Compress old records untuk menghemat space
- Backup history sebelum cleanup

#### **Troubleshooting**
- Query history untuk investigate attachment issues
- Track user behavior patterns
- Audit compliance reporting

---

**Catatan**: Tabel history ini memberikan full audit trail untuk semua perubahan attachment, memungkinkan tracking lengkap dari creation sampai deletion dengan alasan dan user yang bertanggung jawab.

---

## 📋 SUMMARY

### Database Schema Information
- **Total Tables**: 19 tabel
- **Schema Version**: v2.5 (2025-10-30)
- **Framework**: Spring Boot dengan JPA/Hibernate
- **Database**: MySQL/MariaDB

### Key Features
- **Workflow Management**: State machine dengan tabel `workflow`, `workflow_detail`, dan `order_approval`
- **Approval System**: Support untuk approval dengan role-based access
- **Attachment Tracking**: Full audit trail dengan `original_file_name` di tabel `order_attachment_history`
- **Document Reference**: Tabel `rf_document` sebagai master data dokumen
- **Optimistic Locking**: Semua tabel menggunakan `version` column untuk concurrency control
- **Soft Delete**: Pattern soft delete diimplementasikan untuk data retention

### Recent Changes (v2.5)
- ✅ Menambahkan tabel `rf_document` sebagai referensi master dokumen
- ✅ Menambahkan kolom `document_id` dan `keterangan` di tabel `order_attachment`
- ✅ Update struktur tabel `order_attachment_history` dengan kolom `document_id` dan `keterangan`
- ✅ Update entity mappings untuk Spring Boot integration dengan field `documentId` dan `keterangan`
- ✅ Enhanced attachment management dengan referensi dokumen master

**Catatan**: Sistem ini dirancang untuk fleksibilitas maksimal sambil mempertahankan data integrity dan performance yang baik untuk Spring Boot applications.
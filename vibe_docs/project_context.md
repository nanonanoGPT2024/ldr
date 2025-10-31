## 📝 Summary

- Sistem manajemen LDR dengan workflow approval
- Fitur autentikasi dan otorisasi berbasis role
- Manajemen lampiran file untuk order
- Sistem approval multi-level
- OrderData management dengan comprehensive fields
- Dropdown reference tables untuk data consistency

## Business Requirements

### Core Features

1. **Order Management System**

   - Pembuatan order baru dengan OrderData entity
   - Tracking status order melalui workflow (DRAFT → SUBMITTED → IN_PROGRESS → COMPLETED)
   - History perubahan order dengan audit trail
   - Dashboard monitoring order dengan role-based views (request, tracking, final)
   - Comprehensive order fields: client info, cooperation details, financial data, timeline management

2. **Authentication & Authorization**

   - Login/logout dengan JWT
   - Role-based access control (BD, Legal, Admin)
   - Session management
   - Password encryption

3. **File Attachment Management**

   - Upload lampiran ke order
   - Multiple file types support (PDF, DOC, XLS, images)
   - File versioning
   - Secure file storage

4. **Approval System**

   - Multi-level approval workflow
   - Notification system untuk approver
   - Approval history tracking
   - Rejection dengan alasan

5. **Reference Data Management**
   - Dropdown endpoints untuk reference tables
   - Order Status, Priority, Cooperation Type, Document Type
   - Employment Status, Service Cost Type, Document Source
   - Centralized data consistency

### User Roles

- **BD**: Create orders, view draft orders, submit for approval
- **Legal**: Review submitted orders, approve/reject orders
- **Admin**: Full access, user management, system configuration

### Business Rules

- Order harus melalui approval workflow sebelum processing
- File attachments maksimal 10MB per file
- Approval workflow: BD → Legal → Admin (conditional)
- Semua actions harus logged untuk audit trail
- OrderData memiliki comprehensive validation rules
- Reference tables ensure data consistency across orders

### Constraints

- Sistem harus scalable untuk 1000+ concurrent users
- Response time < 2 detik untuk API calls
- 99.9% uptime requirement
- Compliance dengan data protection regulations
- OrderData entity supports complex business workflows

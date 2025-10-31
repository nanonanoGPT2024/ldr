## 📝 Summary

- Architecture layered dengan Spring Boot
- Security menggunakan JWT authentication
- Database JPA/Hibernate dengan MySQL
- API RESTful dengan Swagger documentation
- OrderData entity dengan comprehensive business fields
- Dropdown controller untuk reference data management

## Architecture Decisions

### Overall Architecture

- **Pattern**: Layered Architecture (Presentation → Business → Data Access)
- **Framework**: Spring Boot 3.1.x untuk rapid development
- **Build Tool**: Maven untuk dependency management
- **Deployment**: JAR packaging untuk container-ready deployment

### Layer Structure

```
Controller Layer (REST API)
    ↓
Service Layer (Business Logic)
    ↓
Repository Layer (Data Access)
    ↓
Database (MySQL)
```

### Security Implementation

- **Authentication**: JWT (JSON Web Tokens)
- **Authorization**: Role-based dengan Spring Security (BD, Legal, Admin roles)
- **Password Storage**: BCrypt hashing
- **Session Management**: Stateless dengan JWT

### Database Design

- **ORM**: JPA/Hibernate untuk object-relational mapping
- **Database**: MySQL 8.0 untuk reliability dan performance
- **Schema**: Normalized design dengan foreign key relationships
- **Migration**: Flyway untuk database versioning
- **OrderData Entity**: Comprehensive model dengan 40+ fields untuk business requirements

### API Design

- **Style**: RESTful API dengan proper HTTP methods
- **Documentation**: SpringDoc OpenAPI 2.2.x untuk Swagger UI
- **Versioning**: URL-based versioning (/api/v1/)
- **Response Format**: JSON dengan consistent structure
- **OrderData Endpoints**: CRUD operations dengan role-based filtering
- **Dropdown Endpoints**: Reference table access dengan unified interface

### OrderData Model Architecture

- **Entity Fields**: 40+ fields covering client info, cooperation details, financial data, workflow status
- **Relationships**: Multiple @ManyToOne relationships dengan reference tables
- **Audit Fields**: Created/updated timestamps, user tracking, versioning
- **Soft Delete**: isDeleted flag dengan deletedAt timestamp
- **Validation**: JPA validation annotations untuk data integrity

### Controller Design

- **OrderDataController**: Complex CRUD dengan role-based views (request, tracking, final)
- **DropdownController**: Unified endpoint untuk semua reference tables
- **Security**: JWT authentication required untuk semua endpoints
- **Pagination**: Spring Data Pageable untuk large datasets
- **Filtering**: Advanced filtering berdasarkan role, status, dates

### File Management

- **Storage**: Local file system dengan organized directory structure
- **Security**: File type validation dan size limits
- **Access**: Secure download dengan authentication
- **Metadata**: Database tracking untuk file information

### Development Tools

- **IDE**: IntelliJ IDEA atau VS Code dengan Java extensions
- **Testing**: JUnit 5 + Mockito untuk unit testing
- **Logging**: SLF4J dengan Logback
- **Code Quality**: Checkstyle + PMD untuk static analysis

### Performance Considerations

- **Caching**: Redis untuk session dan frequently accessed data
- **Connection Pooling**: HikariCP untuk database connections
- **Async Processing**: Spring @Async untuk background tasks
- **Monitoring**: Spring Actuator untuk health checks
- **Query Optimization**: JPA specifications untuk complex filtering

### Scalability

- **Horizontal Scaling**: Stateless design memungkinkan multiple instances
- **Load Balancing**: Nginx atau cloud load balancer
- **Database Scaling**: Read replicas untuk high read workloads
- **CDN**: Untuk static file serving
- **Reference Tables**: Cached dropdown data untuk performance

## Tech Stack
- Language: Java 17
- Framework: Spring Boot 3.1.x
- Build Tool: Maven
- Database: MySQL 8.0
- Security: JJWT
- API Documentation: SpringDoc OpenAPI 2.2.x

## Prerequisites
- Java 17 JDK (Oracle JDK atau OpenJDK)
- Maven 3.8+
- MySQL 8.0 Server
- Git (untuk version control)

## Setup Instructions
1. **Install Java 17:**
   ```bash
   # Download dan install Java 17 dari oracle.com atau openjdk.net
   # Verifikasi instalasi:
   java -version
   # Output harus menunjukkan Java 17.x.x
   ```

2. **Install Maven:**
   ```bash
   # Download Maven dari maven.apache.org
   # Extract ke folder pilihan, tambahkan ke PATH
   mvn -version
   # Output harus menunjukkan Maven 3.8+
   ```

3. **Install MySQL 8.0:**
   ```bash
   # Download MySQL dari mysql.com
   # Install sebagai service
   # Buat database baru untuk project:
   mysql -u root -p
   CREATE DATABASE ldr_management;
   ```

4. **Clone atau setup project:**
   ```bash
   git clone <repository-url>
   cd <project-directory>
   ```

## How to Run
### Development
```bash
# Jalankan aplikasi Spring Boot
mvn spring-boot:run
# Aplikasi akan berjalan di http://localhost:8080
```

### Testing
```bash
# Jalankan unit tests
mvn test
```

### Production Build
```bash
# Build JAR file
mvn clean package
# Jalankan JAR
java -jar target/order-management-0.0.1-SNAPSHOT.jar
```

## Environment Variables

Buat file `application.properties` atau `application.yml` di `src/main/resources/`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ldr_management
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JWT Configuration
jwt.secret=your-jwt-secret-key-here
jwt.expiration=86400000

# Server Configuration
server.port=8080
```

## Troubleshooting

- **Port 8080 sudah digunakan:** Ubah `server.port` di application.properties
- **Database connection error:** Pastikan MySQL service berjalan dan credentials benar
- **Java version error:** Pastikan JAVA_HOME menunjuk ke Java 17
- **Maven dependency error:** Jalankan `mvn clean install` untuk resolve dependencies
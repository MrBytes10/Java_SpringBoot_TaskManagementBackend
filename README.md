# 📋 Task Management Backend – Java & Spring Boot

This is a Java 21 backend for managing tasks, built with Spring Boot. It includes user authentication, secure password storage, and full CRUD operations for tasks.

### ✅ Key Features

- User signup/signin with JWT (JSON Web Tokens)
- BCrypt-hashed passwords
- CRUD for tasks (create, read, update, delete)
- Task filtering by status (TODO, IN_PROGRESS, DONE)
- Swagger UI for API documentation
- SQL Server support
- Increased request size limit (32KB)

---

## ⚙️ Tech Stack

- **Java 21**
- **Spring Boot 3.2+**
- **Spring Security + JWT**
- **Spring Data JPA + Hibernate**
- **SQL Server** (configured by default)
- **Maven** for builds
- **Lombok** to reduce boilerplate
- **Swagger/OpenAPI** for API docs

---

## 🚀 Getting Started

### Prerequisites

- Java 21+
- Maven 3.6+
- SQL Server running locally or remotely

### 1. Clone the Project

```bash
git clone <your-repo-url>
cd task-management-backend
```

### 2. Configure `application.yml`

Update your database settings for SQL Server:

```yaml
spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=TaskDb
    username: your_user
    password: your_password
    driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver
  servlet:
    multipart:
      max-request-size: 32KB
      max-file-size: 32KB
```

> 🛡️ Make sure SQL Server is running and accepting TCP connections on port `1433`.

### 3. Run the App

```bash
mvn spring-boot:run
```

Visit: `http://localhost:8080/swagger-ui.html` to explore the API.

---

## 🔐 Authentication

Use `/signup` to register and `/signin` to log in. The response contains a JWT token for use in authenticated requests (add it as a `Bearer` token in headers).

---

## 🧪 Testing

You can run unit tests with:

```bash
mvn test
```

---

## ✅ SOLID Principles Applied

- **SRP**: Each class has a single responsibility (e.g. `TaskService` handles task logic only).
- **OCP**: Logic can be extended via interfaces without modifying core classes.
- **LSP**: Interfaces and base types are respected across the code.
- **ISP**: Smaller interfaces like `IAuthService` and `ITaskService` are created per feature.
- **DIP**: High-level services depend on abstractions, injected via constructor injection.

---

## 📄 License

This project is open-source. Use freely under your preferred license.

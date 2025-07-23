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
- **Spring Security**
- **JWT:** Enables stateless, scalable authentication.
- **BCrypt:** Provides strong password hashing for user security.
- **Spring Data JPA/Hibernate:** Simplifies database access and migrations.
- **SQL Server** (configured by default)
- **Maven** for builds
- **Lombok** to reduce boilerplate
- **Swagger:** Automatically documents the API for easy testing and integration.


## 🗄️ Database Schema

The application uses two main tables: `users` and `tasks`.

### users

| Column      | Type             | Constraints                | Description                |
|-------------|------------------|----------------------------|----------------------------|
| id          | UNIQUEIDENTIFIER | PRIMARY KEY                | User’s unique ID           |
| email       | NVARCHAR(255)    | NOT NULL, UNIQUE           | User’s email address       |
| password    | NVARCHAR(255)    | NOT NULL                   | BCrypt-hashed password     |
| name        | NVARCHAR(100)    |                            | User’s display name        |
| created_at  | DATETIME2        | NOT NULL, DEFAULT GETDATE()| Account creation timestamp |

### tasks

| Column      | Type             | Constraints                | Description                |
|-------------|------------------|----------------------------|----------------------------|
| id          | UNIQUEIDENTIFIER | PRIMARY KEY                | Task’s unique ID           |
| user_id     | UNIQUEIDENTIFIER | NOT NULL, FK to users(id)  | Owner of the task          |
| title       | NVARCHAR(255)    | NOT NULL                   | Task title                 |
| description | NVARCHAR(1000)   |                            | Task details               |
| status      | NVARCHAR(20)     | NOT NULL, DEFAULT 'TODO'   | Task status (TODO, etc.)   |
| created_at  | DATETIME2        | NOT NULL, DEFAULT GETDATE()| Task creation timestamp    |
| updated_at  | DATETIME2        |                            | Last update timestamp      |

- Each task is linked to a user via `user_id`.
- Deleting a user will cascade and delete their tasks.

---

## 🔒 Security & Authentication

- **JWT Authentication:** All endpoints (except `/api/auth/**` and Swagger docs) require a valid JWT token.
- **Password Storage:** User passwords are securely hashed using BCrypt.
- **Stateless Sessions:** The backend does not use server-side sessions; all authentication is stateless via JWT.
- **Authorization:** All authenticated users have the same access level (no roles implemented yet).

---

## 🧩 Maintainability & Scalability

- **Layered Architecture:** The codebase is organized into controllers, services, repositories, and DTOs for clear separation of concerns.
- **Interfaces:** Service interfaces (`IAuthService`, `ITaskService`) allow for easy extension and testing.
- **DTOs:** Data Transfer Objects decouple internal models from API contracts, making it easier to evolve the API.
- **SOLID Principles:** The code follows SOLID principles for maintainability and extensibility.

---


---

## 📚 API Overview

| Endpoint                | Method | Description                        | Auth Required |
|-------------------------|--------|------------------------------------|--------------|
| `/api/auth/signup`      | POST   | Register a new user                | No           |
| `/api/auth/signin`      | POST   | Authenticate and get JWT           | No           |
| `/api/tasks`            | GET    | List all tasks for current user    | Yes          |
| `/api/tasks`            | POST   | Create a new task                  | Yes          |
| `/api/tasks/{id}`       | GET    | Get a specific task                | Yes          |
| `/api/tasks/{id}`       | PUT    | Update a task                      | Yes          |
| `/api/tasks/{id}`       | DELETE | Delete a task                      | Yes          |

- All `/api/tasks` endpoints require a valid JWT in the `Authorization: Bearer <token>` header.

---


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


---

## ✅ SOLID Principles Applied

- **SRP**: Each class has a single responsibility (e.g. `TaskService` handles task logic only).
- **OCP**: Logic can be extended via interfaces without modifying core classes.
- **LSP**: Interfaces and base types are respected across the code.
- **ISP**: Smaller interfaces like `IAuthService` and `ITaskService` are created per feature.
- **DIP**: High-level services depend on abstractions, injected via constructor injection.

---
## 🚀 Extending the App

- The modular structure and use of interfaces make it easy to add new features (e.g., new task fields, additional endpoints) without major refactoring.
- To add new user properties or task features, update the model, DTO, and migration scripts accordingly.

## 📄 License

This project is open-source. Use freely under your preferred license.

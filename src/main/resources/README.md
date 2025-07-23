# Task Management Backend - Java & Spring Boot

This project is a complete backend application for a Task Management system, built with Java 21 and the Spring Boot framework. It provides a RESTful API for user authentication and full CRUD (Create, Read, Update, Delete) functionality for tasks. This serves as a Java-based counterpart to an original C# implementation.

## Features

- **User Authentication**: Secure sign-up and sign-in using JWT (JSON Web Tokens).
- **Password Security**: Passwords are securely hashed using BCrypt.
- **Task Management**: Full CRUD operations for tasks (create, view, update, delete).
- **Task Filtering**: Retrieve tasks by their status (e.g., TODO, IN_PROGRESS, DONE).
- **API Documentation**: Integrated Swagger UI for easy API exploration and testing.
- **Validation**: Server-side validation of incoming data.
- **Structured Logging**: Clear and informative logging for monitoring and debugging.

## Technologies Used

- **Java 21**: The latest long-term support (LTS) version of Java.
- **Spring Boot 3.2+**: For building robust, stand-alone, production-grade applications.
- **Spring Security**: For handling authentication and authorization.
- **Spring Data JPA**: For data persistence with repositories.
- **Hibernate**: The underlying ORM (Object-Relational Mapping) framework.
- **H2 Database**: An in-memory database for easy setup and testing. (Can be easily swapped for PostgreSQL).
- **Maven**: For project build and dependency management.
- **JWT (Java Web Token)**: For creating stateless, secure authentication tokens.
- **Swagger/OpenAPI 3**: For API documentation.
- **Lombok**: To reduce boilerplate code (e.g., getters, setters, constructors).
- **JUnit 5 & Mockito**: For unit testing the service layer.

## Getting Started

### Prerequisites

- Java JDK 21 or later
- Apache Maven 3.6+
- An IDE like IntelliJ IDEA or VS Code with Java extensions.

### Running the Application

1.  **Clone the repository:**
    ```bash
    git clone <your-repository-url>
    cd task-management-backend
    ```

2.  **Configure the application:**
    The application is configured to use an in-memory H2 database by default. You can access the H2 console at `http://localhost:8080/h2-console` with the following settings from `application.yml`:
    - **Driver Class**: `org.h2.Driver`
    - **JDBC URL**: `jdbc:h2:mem:taskmgt_db`
    - **User Name**: `sa`
    - **Password**: (leave blank)

    If you wish to use PostgreSQL, comment out the H2 configuration in `src/main/resources/application.yml` and uncomment the PostgreSQL section, filling in your database credentials.

3.  **Build and run the application using Maven:**
    ```bash
    mvn spring-boot:run
    ```

The application will start on `http://localhost:8080`.

## API Endpoints

The API documentation is available via Swagger UI at:
**`http://localhost:8080/swagger-ui.html`**


## SOLID Principles in This Project

This project was built with the SOLID principles in mind to create a maintainable, scalable, and robust codebase. Here’s how each principle was applied:

### S - Single Responsibility Principle (SRP)

*A class should have only one reason to change.*

-   **`TaskService`**: Its sole responsibility is to handle the business logic related to tasks (creating, updating, etc.). It does not deal with HTTP requests or database queries directly.
-   **`AuthController`**: Its only job is to handle incoming HTTP requests for authentication (`/signup`, `/signin`), validate them, and delegate the work to the `AuthService`.
-   **`JwtUtil`**: This class is exclusively responsible for JWT-related operations like creating, validating, and extracting claims from tokens.

### O - Open/Closed Principle (OCP)

*Software entities (classes, modules, functions, etc.) should be open for extension, but closed for modification.*

-   **Service Interfaces (`ITaskService`, `IAuthService`)**: We code against interfaces in our controllers. This means we can introduce new implementations of these services without changing the controller code. For example, we could create a `PremiumTaskService` that implements `ITaskService` with additional features, and Spring's Dependency Injection could swap it in without modifying `TaskController`.
-   **Spring Security Configuration**: The use of filters like `JwtAuthenticationFilter` allows us to extend the security chain with new behavior without altering the core Spring Security classes.

### L - Liskov Substitution Principle (LSP)

*Subtypes must be substitutable for their base types.*

-   While this project does not feature complex inheritance hierarchies, the principle is respected by Spring's design. For instance, `JpaRepository` is an interface with multiple implementations. We can rely on the contract of `JpaRepository` regardless of the underlying database or implementation, knowing that methods like `save()` and `findById()` will behave as expected.

### I - Interface Segregation Principle (ISP)

*No client should be forced to depend on methods it does not use.*

-   **`IAuthService` and `ITaskService`**: We have separate interfaces for authentication and task management. A client like `TaskController` only needs a dependency on `ITaskService` and has no knowledge of the methods in `IAuthService`. This prevents a "fat" interface with unrelated methods and ensures clients only depend on what they need.

### D - Dependency Inversion Principle (DIP)

*High-level modules should not depend on low-level modules. Both should depend on abstractions (e.g., interfaces). Abstractions should not depend on details. Details (concrete implementations) should depend on abstractions.*

-   **Constructor Injection**: This is the most prominent example of DIP in the project. High-level modules like `TaskService` do not depend on a concrete `TaskRepositoryImpl`, but on the `TaskRepository` interface (the abstraction).
    ```java
    // TaskService (high-level) depends on TaskRepository (abstraction)
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    ```
-   Spring's **Inversion of Control (IoC)** container is responsible for creating the concrete implementation of `TaskRepository` and "injecting" it into `TaskService` at runtime. This decouples our components, making the system easier to test and maintain.
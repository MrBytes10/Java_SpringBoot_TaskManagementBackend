-- This script creates the initial tables for the application.

-- Create the users table
CREATE TABLE users (
    id UNIQUEIDENTIFIER PRIMARY KEY,
    email NVARCHAR(255) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    name NVARCHAR(100),
    created_at DATETIME2 NOT NULL DEFAULT GETDATE()
);

-- Create the tasks table
CREATE TABLE tasks (
    id UNIQUEIDENTIFIER PRIMARY KEY,
    user_id UNIQUEIDENTIFIER NOT NULL,
    title NVARCHAR(255) NOT NULL,
    description NVARCHAR(1000),
    status NVARCHAR(20) NOT NULL DEFAULT 'TODO',
    created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME2,
    CONSTRAINT FK_tasks_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
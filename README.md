# OTP Authentication with JWT

## Overview
This application demonstrates a secure, passwordless authentication flow using **OTP (One-Time Password)** combined with **JWT (JSON Web Token)** and **Refresh Tokens**.

### What Does This Application Do?
- Sends an OTP to the user's email for authentication.
- Validates the OTP and generates a JWT access token and a refresh token.
- Supports refreshing access tokens using refresh tokens, ensuring long-term secure access.
- Implements **role-based access control (RBAC)**, enabling restricted access to specific APIs based on user roles.

This project is built using **Spring Boot** and demonstrates secure, modern, and user-friendly authentication mechanisms.

---

## Features
1. **Passwordless Authentication**: Login without remembering passwords—just use OTP sent to your email.
2. **JWT Tokens**: Secure access tokens to protect APIs and provide session-based authentication.
3. **Refresh Tokens**: Allows the user to get a new access token without re-entering the OTP.
4. **Role-Based Access Control**: Restrict access to sensitive APIs based on user roles (e.g., Admin, User).
5. **Email Integration**: Sends OTP via email using JavaMailSender.

---

## Getting Started

### Prerequisites
1. **Java 11 or above**
2. **Maven**
3. **SMTP Email Account** (for sending OTP emails)

### Clone the Repository
```bash  
git clone https://github.com/Akshit-Zatakia/authentication-via-email.git  
cd authentication-via-email
```

### Configure the .env file
Update the application.properties file with your email and SMTP server settings:
```
EMAIL=
EMAIL_PASSWORD=
SECRET_KEY=
DB_USERNAME=
DB_PASSWORD=
```

### Build and Run the Application
1. Build the application using Maven:
    ```bash
    mvn clean install
    ```    

2. Run the application:
    ```bash
       mvn spring-boot:run
    ```


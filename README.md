# 👤 User Service (Spring Boot Microservice)

## 📌 Overview

User Service is a microservice responsible for managing user-related operations in the E-Commerce system.
It handles user creation, validation, and event publishing to other services (like Email Service) using Kafka.

---

## 🚀 Features

* ✅ Create new users
* 🔐 Secure password storage using **BCrypt hashing**
* 📩 Publish user-created events to Kafka
* 📧 Trigger email notifications (via Email Service)
* 🗄️ Store user data in PostgreSQL
* ⚠️ Proper exception handling (User not found, validation errors)
* 🔄 Microservice communication using Kafka

---

## 🛠️ Tech Stack

* Java 17+
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Apache Kafka
* Spring Security (for BCrypt only)
* Lombok

---

## 🧱 Project Structure

```
userEvent
│
├── controller        # REST APIs
├── service           # Business logic
├── repository        # JPA repositories
├── entity            # Database entities
├── event             # Kafka event models
├── config            # Security & Kafka config
└── exception         # Custom exceptions
```

---

## 📦 API Endpoints

### 🔹 Create User

```
POST /users
```

### 📥 Request Body

```json
{
  "userName": "Satyendra",
  "userDob": "1998-01-01",
  "userAddress": "Lucknow",
  "userMobileNumber": "9876543210",
  "userEmail": "test@gmail.com",
  "pinCode": "226001",
  "password": "12345"
}
```

### 📤 Response

```json
{
  "id": 1,
  "userName": "Satyendra",
  "userEmail": "test@gmail.com"
}
```

---

## 🔐 Password Security

* Passwords are stored using **BCrypt hashing**
* Plain passwords are never stored in DB
* Example stored password:

```
$2a$10$XyzEncryptedHashValue
```

---

## 🧵 Kafka Integration

### 📌 Topic Used

```
user-created
```

### 📤 Event Published

```json
{
  "userId": "1",
  "userName": "Satyendra",
  "email": "test@gmail.com",
  "status": "CREATED"
}
```

### 🔄 Flow

```
User Service → Kafka → Email Service → Email Sent
```

---

## ⚙️ Configuration

### application.yml / properties

```yaml
server:
  port: 9095

spring:
  application:
    name: userEvent

  datasource:
    url: jdbc:postgresql://localhost:5432/shiroyadb
    username: postgres
    password: shiroya

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

  kafka:
    producer:
      bootstrap-servers: localhost:9092
```

---

## ❗ Error Handling

Handled using:

* Custom Exception → `UserNotFoundException`
* Global Handler → `@RestControllerAdvice`

### Example Response

```json
{
  "status": 404,
  "message": "User not found"
}
```

---

## 🧪 How to Run

### 1️⃣ Start Kafka & Zookeeper

### 2️⃣ Start PostgreSQL

### 3️⃣ Run Application

```
mvn spring-boot:run
```

---

## 🔗 Dependencies

* spring-boot-starter-web
* spring-boot-starter-data-jpa
* spring-boot-starter-security
* spring-kafka
* postgresql
* lombok

---

## 📈 Future Enhancements

* 🔐 JWT Authentication
* 👥 Role-based authorization
* 🌐 API Gateway integration
* 🔁 Retry & Dead Letter Queue (Kafka)
* 📊 Logging & Monitoring (ELK)

---

## 👨‍💻 Author

**Satyendra Chaurasiya**

---

## ⭐ Notes

* This service is part of a **microservices-based E-Commerce system**
* Works with:

    * Order Service
    * Product Service
    * Email Service

---

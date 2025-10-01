
# 💸 khmer_online_banking_system_project

---

## 🚀 Features
- Spring Boot version 4.x (Java)
- PostgreSQL database integration
- JPA/Hibernate ORM
- Global exception handling
- Unit & integration tests
- API documentation with Swagger/OpenAPI

---

## 📦 Prerequisites
Before running the project, ensure you have installed:
- Java 21+
- Gradle 8.4+ (or use the included Gradle wrapper `./gradlew`)
- PostgreSQL 14+
- Git

---

## 📦 Installation

1. **Clone the repository**

```bash
git clone https://github.com/menglysun/khmer_online_banking.git
cd khmer_online_banking
```

2. **Configure Database**

Update your src/main/resources/application.properties:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/online_banking_db
spring.datasource.username=user
spring.datasource.password=DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
---

3. **Build Project (optional)**

```bash
gradle clean build
```

4. **Run Application**

```bash
gradle bootRun
```

The app will start at: 👉 http://localhost:8082
----

### 📖 API Documentation
Swagger UI available at: 👉 http://localhost:8082/swagger-ui/index.html

---

5. **Run script to create role**

➡️ src/main/resources/data/script.sql

```sql
INSERT INTO "public"."role" ("id", "status", "version", "created_at", "updated_at", "name", "role") VALUES (1, 't', 0, null, null, 'Admin', 'ADMIN');
INSERT INTO "public"."role" ("id", "status", "version", "created_at", "updated_at", "name", "role") VALUES (2, 't', 0, null, null, 'User', 'USER');
```

----

---
# 📂 Project Structure**
```text
├── pom.xml / build.gradle # Build tool config
├── README.md # Documentation
├── src
│ ├── main
│ │ ├── java
│ │ │ └── com.mengly.dev.khmer_online_banking
│ │ │ ├── ProjectApplication.java # Main Spring Boot entry
│ │ │
│ │ │ ├── controller # Controllers (C)
│ │ │ │ └── UserController.java
│ │ │
│ │ │ ├── model # Models / Entities (M)
│ │ │ │ └── User.java
│ │ │
│ │ │ ├── service # Business logic
│ │ │ │ └── UserService.java
│ │ │
│ │ │ ├── repository # Data access layer
│ │ │ │ └── UserRepository.java
│ │ │
│ │ │ └── config # Config classes (security, beans, etc.)
│ │ │ └── WebConfig.java
│ │ │
│ │ └── resources
│ │ ├── application.properties / application.yml
│ │ ├── static # Static files (css, js, images)
│ │
│ └── test
│ └── java
│ └── com.example.project
│ └── UserServiceTests.java
```

---

# Sample User Registration

```
POST http://localhost:8082/api/auth/register
```

```json
{
    "username": "userTest",
    "password": "12345",
    "firstName": "User",
    "lastName": "Test",
    "phoneNumber": "0973545458",
    "email": "userTest@gmail.com",
    "gender": "MALE",
    "roles": [
        {
            "id": 2,
            "role": "USER"
        }
    ]
}
```

---

# Sample Login

```
POST http://localhost:8082/api/auth/login
```
```json
{
    "usernameOrEmail": "userTest",
    "password": "123456"
}
```

---

## Author

**Sun Mengly**

---

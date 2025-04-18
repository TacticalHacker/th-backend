# TH-Scribes Backend

This is the backend service for **TH-Scribes**, a client onboarding and digital contract management platform built for writers under the Tactical Hacker organization.

---

## 📦 Overview

The backend is built using **Spring Boot** and exposes RESTful APIs for:
- Admin operations (inviting clients, assigning contracts)
- Client operations (accepting onboarding, signing contracts)
- Secure contract generation using predefined templates
- Role-based authentication and audit logging

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot 3.x
- Spring Security + JWT
- PostgreSQL
- Maven
- Docker (optional)
- JavaMailSender (for email)

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/TacticalHacker/th-scribes-backend.git
cd th-scribes-backend
```

### 2. Configure Environment

Create an `.env` file or set the following variables in `application.properties`:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `MAIL_USERNAME`
- `MAIL_PASSWORD`
- `JWT_SECRET`

### 3. Build & Run

```bash
./mvnw clean package
./mvnw spring-boot:run
```

---

## 🧪 Running Tests

```bash
./mvnw test
```

---

## 📃 Functional Requirements

All detailed requirements are available in [FunctionalRequirement.md](./FunctionalRequirement.md)

---

## 📌 API Summary

| Endpoint                 | Method | Description                                 |
|--------------------------|--------|---------------------------------------------|
| `/api/auth/login`         | POST   | Authenticate user and return JWT           |
| `/api/admin/invite`       | POST   | Send onboarding email to client            |
| `/api/admin/contracts`    | POST   | Generate and assign a contract             |
| `/api/client/contracts`   | GET    | View assigned contracts                    |
| `/api/client/sign`        | POST   | Digitally sign a contract                  |

---

## 🤝 Contribution

- Fork the repository
- Create a new branch (`git checkout -b feature/feature-name`)
- Commit your changes (`git commit -am 'Add feature'`)
- Push to the branch (`git push origin feature/feature-name`)
- Open a Pull Request

---

## 📄 License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).

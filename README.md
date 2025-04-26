# TH Backend

This is the backend service for **Tactical Hacker**, a comment backend service for all sub-orgs under the Tactical Hacker organization.

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
git clone https://github.com/TacticalHacker/th-backend.git
cd th-backend
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

## Architecture Diagram

![th-backend drawio](https://github.com/user-attachments/assets/808c86ca-cc39-431a-8a2e-8c3822cb0a4a)

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

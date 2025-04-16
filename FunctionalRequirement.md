# 📄 TH-Scribes Functional Requirements

## 1. 🧩 Overview

**TH-Scribes** is a streamlined web application for onboarding clients (primarily writers), dynamically generating digital contracts from a predefined template, and tracking their signature status. It supports **Admin** and **Client** roles and automates the digital signing process without requiring manual contract uploads.

---

## 2. 👥 User Roles

### 🔑 Admin
- Onboard clients by filling a basic form.
- Generate client-specific contracts from a static HTML template.
- Resend onboarding invitations.
- Track contract signature status.
- View audit logs of client interactions.

### ✍️ Client
- Receives onboarding email with secure login link.
- Securely logs in using a tokenized "magic link".
- Views their personalized contract.
- Digitally signs or rejects the contract with optional comments.
- Downloads the signed contract.

---

## 3. ⚙️ Core Functionalities

### 3.1 Authentication & Security
- Magic link-based login for clients (email token).
- Admin login via email/password using JWT.
- Role-based access control.
- Expiry-based login links for added security.

### 3.2 Admin Dashboard
- Visual overview:
    - Total onboarded clients
    - Contract status: Pending / Signed / Rejected
- Functionalities:
    - Add client (Name, Email, Start/End dates)
    - Auto-generate contract
    - Send onboarding invite with embedded secure link
    - Resend or revoke onboarding access

### 3.3 Contract Generation System
- Uses a **predefined HTML template** for contracts.
- Auto-populates dynamic fields:
    - Client Name
    - Email
    - Start & End Dates
    - Current Date
- Exports signed contract to **PDF** upon completion.

### 3.4 Email System (Automated)
- Triggers on:
    - New onboarding
    - Contract viewed
    - Contract signed/rejected
    - Pending contract reminders (if unsigned after X days)
- Email contains secure magic link for client login.

### 3.5 Client Portal
- Clean UI after login:
    - Welcome section
    - Contract preview
    - Sign or reject option (with optional comments)
- Post-signature:
    - Download signed PDF
    - Status updated in the system

### 3.6 Audit Logging
- Tracks key events:
    - Contract generation
    - Emails sent
    - Link clicks
    - Signatures / rejections
- Viewable by Admin per-client in timeline format.

---

## 4. 🖥️ Tech Stack

- **Frontend**: React + Vite (deployed on GitHub Pages)
- **Backend**: Java 21 + Spring Boot 3 + PostgreSQL (deployed on Render)
- **PDF Generation**: Flying Saucer / iText
- **Email Service**: Spring Mail / SendGrid
- **Authentication**: JWT (Admin), Magic Link Token (Client)

---

## 5. 📱 UI & UX Highlights

- Mobile-first responsive design
- Minimal client interface (only what’s necessary)
- Admin dashboard with filtering, search, status indicators
- Optional: Dark/Light mode toggle

---

## 6. 📌 Future Scope

- Public client showcase (read-only)
- Onboarding progress tracking
- Contract versioning support
- Admin collaboration features (multiple admins)

---

> ✅ Built for scalability, automation, and ease of use. Simplifying client onboarding with contract management, all from a single panel.

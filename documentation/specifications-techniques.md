# Technical Specifications – SOA University System

## 1. Global Architecture

The system is composed of several independent services following a **Service-Oriented Architecture (SOA)**:

- **Auth Service**: Spring Boot (REST, JWT)
- **Student Service**: Node.js / Express (REST)
- **Grade Service**: Node.js / Express (REST)
- **Course Service**: Spring Boot (SOAP)
- **Billing Service**: Spring Boot (SOAP)
- **API Gateway**: Spring Cloud Gateway (request routing)

All services expose HTTP APIs accessible through the API Gateway.

---

## 2. Data Model (Simplified View)

### 2.1 Users (Auth Service)

- `id`: Long
- `username`: String (unique)
- `password`: String (hashed)
- `email`: String
- `role`: enum { ADMIN, TEACHER, STUDENT }

---

### 2.2 Students (Student Service)

- `id`: String / ObjectId
- `firstName`
- `lastName`
- `email`
- `department`
- `level`
- `group`
- `nationalId` or `studentId`

---

### 2.3 Courses (Course Service – SOAP)

- `id`: Long
- `code`: String
- `title`: String
- `teacherId`: Long or String
- `level`
- `group`
- `schedule`: String (day + time slot)

---

### 2.4 Grades (Grade Service)

- `id`
- `studentId`
- `courseId`
- `noteDS`
- `noteExam`
- `average`
- `status`: PASSED / RETAKE

---

### 2.5 Billing (Billing Service)

- `id`
- `studentId`
- `registrationFee`
- `tuitionFee`
- `penalties`
- `total`
- `status`: PAID / UNPAID

---

## 3. Service Specifications

### 3.1 Auth Service (Spring Boot – REST)

**Base URL**: `/auth`

Endpoints:

- `POST /auth/register`
  - Body: `{ username, password, email, role }`
  - Function: register a new user and return a JWT.
- `POST /auth/login`
  - Body: `{ username, password }`
  - Function: authenticate the user and return a JWT.
- `GET /auth/me`
  - Header: `Authorization: Bearer <token>`
  - Function: return the authenticated user’s information.

**Security**:
- Spring Security with JWT filter.
- Passwords hashed using BCrypt.

---

### 3.2 Student Service (Node.js – REST)

**Base URL**: `/students`

Endpoints:

- `GET /students` – list all students.
- `GET /students/:id` – retrieve student details.
- `POST /students` – create a student.
- `PUT /students/:id` – update a student.
- `DELETE /students/:id` – delete a student.

All requests are protected using the header:
`Authorization: Bearer <token>`.

---

### 3.3 Grade Service (Node.js – REST)

**Base URL**: `/grades`

Endpoints:

- `POST /grades` – add a grade.
- `GET /grades/student/:studentId` – list grades for a student.
- `GET /grades/student/:studentId/average` – calculate the average and status (PASSED / RETAKE).

Business logic example:

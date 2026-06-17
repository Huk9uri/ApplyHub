# AGENTS.md - ApplyHub Backend

## 1. Backend Role

The backend is the core portfolio strength of ApplyHub.
It must not be a simple CRUD server.
It should demonstrate Spring Boot-based full-stack backend capability.

Main backend responsibilities:

- Authentication and authorization
- User-specific data access control
- Application CRUD
- Application status change logic
- Status history recording
- ActivityLog creation
- Grass data aggregation
- Dashboard summary aggregation
- Validation and global exception handling

## 2. Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Security
- JWT
- Spring Data JPA
- MySQL 8
- Bean Validation
- Lombok
- Springdoc OpenAPI / Swagger
- JUnit5

## 3. Package Structure

Prefer a domain-based package structure.

```text
backend/src/main/java/com/applyhub/
├── ApplyHubApplication.java
├── auth/
│   ├── controller/
│   ├── service/
│   ├── dto/
│   └── jwt/
├── user/
│   ├── domain/
│   ├── repository/
│   └── service/
├── application/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── domain/
│   └── dto/
├── question/
├── tag/
├── activity/
├── dashboard/
└── global/
    ├── config/
    ├── security/
    ├── exception/
    └── response/
```

Do not create unnecessary layers before they are needed.

## 4. Core Entities

Minimum entities:

- User
- Application
- ApplicationStatusHistory
- ActivityLog

Second-phase entities:

- CoverLetterQuestion
- TechTag
- ApplicationTechTag

## 5. Domain Rules

### Application Ownership

Every Application belongs to one User.
Authenticated users can only access their own applications.

All read, update, delete, status change operations must check ownership.

### Status Change

Changing an application status must not be a simple field update.

When status changes:

1. Find the application.
2. Check ownership.
3. Store previous status.
4. Update status.
5. Create ApplicationStatusHistory.
6. If new status is APPLIED:
   - Set appliedAt if not already set.
   - Create ActivityLog with type APPLIED.
7. Execute the operation in one transaction.

Use `@Transactional` for this logic.

### ActivityLog

ActivityLog is the source for grass visualization and dashboard activity stats.

Activity types:

```java
APPLICATION_CREATED,
STATUS_CHANGED,
APPLIED,
COVER_LETTER_UPDATED
```

Do not calculate grass data directly from Application only.
Use ActivityLog as the main source of activity records.

## 6. Suggested Entity Fields

### User

```text
id
email
passwordHash
nickname
createdAt
updatedAt
```

### Application

```text
id
user
companyName
position
jobUrl
platform
status
deadline
appliedAt
memo
portfolioVersion
createdAt
updatedAt
```

### ApplicationStatusHistory

```text
id
application
fromStatus
toStatus
changedAt
```

### ActivityLog

```text
id
user
application
type
occurredAt
```

### CoverLetterQuestion

```text
id
application
question
answer
orderIndex
createdAt
updatedAt
```

### TechTag

```text
id
name
createdAt
```

## 7. Enum Values

### ApplicationStatus

```java
public enum ApplicationStatus {
    INTERESTED,
    PLANNED,
    WRITING,
    APPLIED,
    DOCUMENT_PASSED,
    DOCUMENT_FAILED,
    CODING_TEST,
    INTERVIEW,
    FINAL_PASSED,
    FINAL_FAILED,
    ON_HOLD
}
```

### ActivityType

```java
public enum ActivityType {
    APPLICATION_CREATED,
    STATUS_CHANGED,
    APPLIED,
    COVER_LETTER_UPDATED
}
```

### JobPlatform

```java
public enum JobPlatform {
    MANUAL,
    JOBKOREA,
    SARAMIN,
    WANTED,
    PROGRAMMERS,
    LINKEDIN,
    OTHER
}
```

## 8. API Design

### Auth API

```http
POST /api/auth/signup
POST /api/auth/login
GET /api/users/me
```

### Application API

```http
GET /api/applications
POST /api/applications
GET /api/applications/{id}
PUT /api/applications/{id}
DELETE /api/applications/{id}
PATCH /api/applications/{id}/status
```

### Activity API

```http
GET /api/activities/grass?year=2026
GET /api/activities/recent
```

### Dashboard API

```http
GET /api/dashboard/summary
```

### Question API - Phase 2

```http
POST /api/applications/{applicationId}/questions
PUT /api/questions/{id}
DELETE /api/questions/{id}
```

### Tag API - Phase 2

```http
GET /api/tags
POST /api/tags
PUT /api/applications/{applicationId}/tags
```

## 9. Validation Rules

Use Bean Validation on request DTOs.

Examples:

- email must be valid
- password must not be blank
- companyName must not be blank
- position must not be blank
- jobUrl must be a valid URL if provided
- memo length should be limited
- status must be one of ApplicationStatus values

Do not trust frontend validation.

## 10. Error Handling

Use a global exception handler.

Recommended error cases:

- 400 Bad Request: validation failure
- 401 Unauthorized: missing or invalid token
- 403 Forbidden: accessing another user's resource
- 404 Not Found: resource not found
- 409 Conflict: duplicate email or duplicate job URL
- 500 Internal Server Error: unexpected server error

Use consistent error response format.

Example:

```json
{
  "status": 400,
  "message": "회사명은 필수입니다.",
  "path": "/api/applications"
}
```

## 11. Transaction Rules

Use `@Transactional` for operations that change multiple tables.

Required transactional cases:

- Signup if additional profile records are created
- Application status change
- Application delete with related data
- Tag replacement for an application
- Cover letter question reorder if implemented

The most important transaction is status change:

```text
Application update + StatusHistory insert + ActivityLog insert
```

## 12. Repository Rules

- Use Spring Data JPA repositories.
- Define query methods clearly.
- Use fetch join or EntityGraph when needed to avoid N+1 problems.
- Keep complex dashboard aggregation queries in repository or query service.

Examples:

```java
findByIdAndUserId(Long id, Long userId)
findAllByUserId(Long userId)
existsByUserIdAndJobUrl(Long userId, String jobUrl)
```

## 13. Dashboard and Grass Rules

### Grass API

The grass API returns date-based activity counts.

Example response:

```json
{
  "year": 2026,
  "days": [
    {
      "date": "2026-06-11",
      "count": 2
    }
  ]
}
```

Only authenticated user's activities should be included.

### Dashboard Summary API

Return:

- total application count
- count by status
- weekly applied count
- monthly applied count
- upcoming deadlines
- recent applications
- streak days if implemented

## 14. Security Rules

- Store password as hash only.
- Use BCryptPasswordEncoder.
- Use JWT access token.
- Do not store raw passwords.
- Do not expose passwordHash in responses.
- All protected APIs require authentication.
- CORS must allow the local frontend origin only in development.

## 15. Testing Priority

Add tests after core APIs work.

High-value tests:

- Signup success
- Duplicate email failure
- Login success
- Login failure with wrong password
- Create application success
- Cannot access another user's application
- Change status to APPLIED creates ActivityLog
- Delete application removes related records

## 16. Do Not Do

- Do not skip authentication for application APIs.
- Do not let users access other users' data.
- Do not create ActivityLog from frontend.
- Do not let frontend decide appliedAt directly as the source of truth.
- Do not put business logic in controllers.
- Do not return JPA entities directly from controllers.
- Do not implement crawling in backend MVP.

## 17. Portfolio Emphasis

When generating backend code or documentation, emphasize:

- Spring Boot layered architecture
- JPA relationship design
- Spring Security and JWT
- User ownership validation
- Transactional status change logic
- ActivityLog-based grass aggregation
- Dashboard summary aggregation
- Global exception handling
- Bean Validation
- Swagger API documentation

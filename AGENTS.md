# AGENTS.md - ApplyHub Monorepo

## 1. Project Overview

ApplyHub is a full-stack job application management service for job seekers.
Users can register job postings, manage application status, track deadlines, and visualize application activity like GitHub contributions.

The project is structured as a monorepo.

```text
ApplyHub/
├── frontend/          # Next.js frontend
├── backend/           # Spring Boot backend
├── docker-compose.yml # Local MySQL and service orchestration
├── README.md
└── AGENTS.md          # Monorepo-level instructions
```

## 2. Product Goal

The main goal is not to build a simple CRUD app.
The goal is to build a portfolio-worthy full-stack service that demonstrates:

- User-centered problem definition
- Frontend dashboard and state management
- Spring Boot backend API design
- Authentication and authorization
- Relational data modeling with JPA
- Application status history and activity logs
- Dashboard aggregation APIs
- Docker-based local environment

## 3. Core Domain

ApplyHub manages the following domain concepts:

- User
- Application
- ApplicationStatusHistory
- ActivityLog
- CoverLetterQuestion
- TechTag
- ApplicationTechTag

The most important domain behavior is:

```text
When a user changes an application status to APPLIED,
the backend must update the application status,
record status history,
set appliedAt,
and create an ActivityLog in a single transaction.
```

This ActivityLog is later used to generate the contribution-grass style visualization.

## 4. Tech Stack

### Frontend

- Next.js
- TypeScript
- Tailwind CSS
- TanStack Query
- Axios or Fetch wrapper

### Backend

- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Security
- JWT
- Spring Data JPA
- Bean Validation
- MySQL 8
- Lombok
- Swagger / Springdoc OpenAPI

### Infra

- Docker
- Docker Compose
- MySQL container

## 5. Development Priority

Build the project in this order:

1. Monorepo structure
2. Docker Compose MySQL environment
3. Spring Boot backend base setup
4. User authentication
5. Application CRUD
6. Application status change logic
7. ActivityLog and grass API
8. Dashboard summary API
9. Frontend pages and API integration
10. README, Swagger, screenshots, troubleshooting notes

Do not start with advanced features.

## 6. MVP Scope

### Must Have

- Signup
- Login
- JWT authentication
- Create application
- Read application list
- Read application detail
- Update application
- Delete application
- Change application status
- Record ActivityLog when status becomes APPLIED
- Grass API by date
- Dashboard summary API
- Deadline-based sorting or display

### Should Have

- Cover letter question CRUD
- Tech tag management
- Status filter
- Company name search
- Swagger API docs
- Docker Compose execution

### Not MVP

- JobKorea crawling
- AI cover letter generation
- Email parsing
- Calendar integration
- Notification system
- File upload
- Deployment automation

## 7. Cross-Cutting Rules

### Code Quality

- Prefer clear, maintainable code over clever abstractions.
- Keep naming consistent across frontend and backend.
- Avoid premature abstraction.
- Do not create unused layers, files, or interfaces.
- Remove dead code after refactoring.

### Security

- Never hard-code secrets.
- Use environment variables for DB credentials and JWT secret.
- Do not expose password hashes to the frontend.
- All user-owned resources must be accessed only by the authenticated owner.

### API Design

- Use REST-style endpoints.
- Use consistent response shapes.
- Use proper HTTP status codes.
- Validate request bodies.
- Handle errors through global error handling.

### Git / Commit Style

Use concise commit messages.

Examples:

```text
init monorepo structure
setup spring boot backend
add application entity
implement application status change
add activity grass api
connect dashboard summary api
```

## 8. When Making Changes

Before editing code, check:

1. Which layer is affected?
2. Is this feature part of MVP?
3. Does it change the API contract?
4. Does it require frontend and backend changes together?
5. Does it affect authentication or user ownership?
6. Does it affect ActivityLog or dashboard aggregation?

## 9. Do Not Do

- Do not change the stack without explicit instruction.
- Do not replace Spring Boot with Node.js.
- Do not replace MySQL with PostgreSQL unless explicitly asked.
- Do not implement crawling in the MVP.
- Do not add AI features before the core service is complete.
- Do not create large generic utility layers without need.
- Do not bypass backend validation and rely only on frontend validation.

## 10. Portfolio Emphasis

When generating code or documentation, emphasize these points:

- Spring Boot backend structure
- JPA entity relationship design
- JWT authentication
- User-specific data access control
- Transactional status change logic
- ActivityLog-based grass visualization
- Dashboard aggregation API
- Docker Compose local environment
- Clean frontend and backend separation

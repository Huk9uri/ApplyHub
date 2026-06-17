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

## Commit Convention

이 프로젝트는 아래 커밋 컨벤션을 따른다.
커밋 메시지는 작업 목적이 명확히 드러나도록 작성하며, 불필요하게 긴 설명은 본문에 작성한다.

### Commit Message Format

```text
<type>: <description>
```

예시:

```text
feat: 지원 공고 등록 API 추가
fix: 로그인 실패 시 에러 응답 수정
docs: README 실행 방법 수정
style: 불필요한 세미콜론 제거
refactor: 지원 상태 변경 로직 분리
test: ApplicationService 테스트 추가
chore: Gradle 설정 수정
```

### Commit Types

* `feat`: 새로운 기능 추가
* `fix`: 버그 수정
* `docs`: 문서 수정
* `style`: 코드 동작 변경 없이 스타일만 수정
  예: 들여쓰기, 포맷팅, 세미콜론 제거
* `refactor`: 기능 변경 없이 코드 구조 개선
* `test`: 테스트 코드 추가 또는 수정
* `chore`: 코드 변경 없이 설정, 빌드, 패키지, 환경 관련 작업

### Commit Rules

* 커밋 메시지는 한국어로 작성한다.
* 하나의 커밋에는 하나의 목적만 담는다.
* 기능 추가와 리팩토링을 같은 커밋에 섞지 않는다.
* 코드 동작이 바뀌지 않는 포맷팅 작업은 `style`을 사용한다.
* 설정 파일, 의존성, 빌드 스크립트 변경은 `chore`를 사용한다.
* 테스트 코드만 추가하거나 수정한 경우 `test`를 사용한다.
* 문서만 수정한 경우 `docs`를 사용한다.

### Recommended Commit Examples

```text
feat: 회원가입 API 추가
feat: 지원 공고 상태 변경 기능 추가
feat: ActivityLog 기반 잔디 조회 API 추가

fix: JWT 인증 실패 시 500 응답 발생 문제 수정
fix: 마감일이 없는 공고 조회 오류 수정

docs: 프로젝트 실행 방법 추가
docs: API 명세 업데이트

style: 백엔드 코드 포맷 정리
style: 프론트엔드 컴포넌트 들여쓰기 수정

refactor: ApplicationService 상태 변경 로직 분리
refactor: 공통 응답 형식 적용

test: AuthService 로그인 테스트 추가
test: 지원 상태 변경 트랜잭션 테스트 추가

chore: MySQL Docker Compose 설정 추가
chore: Spring Security 의존성 추가
```

### Commit Guidance for Codex

Codex가 작업을 수행할 때는 변경 내용에 맞는 commit type을 선택해야 한다.

* 새로운 API, 화면, 컴포넌트, 도메인 기능을 추가하면 `feat`
* 기존 기능의 오류를 고치면 `fix`
* README, AGENTS.md, API 문서 등을 수정하면 `docs`
* 코드 실행 결과가 변하지 않는 포맷팅만 수행하면 `style`
* 기존 동작을 유지하면서 구조만 개선하면 `refactor`
* 테스트를 추가하거나 수정하면 `test`
* 의존성, Docker, Gradle, npm, 환경 설정을 변경하면 `chore`

커밋 전에는 변경 범위를 확인하고, 서로 다른 목적의 변경이 섞여 있으면 커밋을 분리한다.

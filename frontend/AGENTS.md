# AGENTS.md - ApplyHub Frontend

## 1. Frontend Role

The frontend is responsible for providing a clear dashboard for job seekers to manage applications, deadlines, statuses, and activity records.

The frontend must make the user feel:

```text
I can quickly see which applications need attention,
what I have already applied to,
and whether I am keeping up my job search routine.
```

## 2. Tech Stack

- Next.js
- TypeScript
- Tailwind CSS
- TanStack Query
- Axios or a custom fetch client
- App Router

## 3. Directory Structure

Use a clear structure under `frontend/src`.

```text
frontend/
└── src/
    ├── app/
    │   ├── page.tsx
    │   ├── login/
    │   ├── signup/
    │   ├── applications/
    │   │   ├── page.tsx
    │   │   ├── new/
    │   │   └── [id]/
    │   └── layout.tsx
    │
    ├── components/
    │   ├── common/
    │   ├── dashboard/
    │   ├── application/
    │   └── grass/
    │
    ├── features/
    │   ├── auth/
    │   ├── applications/
    │   ├── dashboard/
    │   └── activities/
    │
    ├── lib/
    │   ├── apiClient.ts
    │   └── auth.ts
    │
    ├── hooks/
    │   ├── queries/
    │   └── mutations/
    │
    ├── types/
    │   ├── auth.ts
    │   ├── application.ts
    │   ├── activity.ts
    │   └── dashboard.ts
    │
    └── utils/
        ├── date.ts
        └── status.ts
```

If the project is still small, avoid over-engineering. Keep structure simple and grow only when needed.

## 4. Core Pages

### Dashboard Page `/`

Show:

- Weekly application count
- Monthly application count
- Total application count
- Applications by status
- Upcoming deadlines
- Recent applications
- Activity grass

### Application List Page `/applications`

Show:

- Application list
- Search by company name
- Filter by status
- Sort by deadline
- Status badge
- Deadline badge
- Link to detail page

### Application Create Page `/applications/new`

Input fields:

- Company name
- Position
- Job posting URL
- Platform
- Deadline
- Memo

### Application Detail Page `/applications/[id]`

Show and edit:

- Company name
- Position
- Job URL
- Status
- Deadline
- Memo
- Applied date
- Status history if available

## 5. UX Principles

- The dashboard should show what the user should do next.
- Do not make the registration form too heavy.
- Status changes must be easy and visible.
- Empty states must encourage action, not blame the user.
- Deadline warnings must be visually clear.
- Grass visualization is motivational, but it must not overwhelm the main workflow.

Good empty state examples:

```text
아직 등록한 공고가 없습니다. 첫 번째 공고를 추가해볼까요?
이번 주 지원 기록이 없습니다. 관심 공고를 하나 저장해보세요.
```

Avoid:

```text
아직 지원하지 않았습니다.
지원 수가 부족합니다.
```

## 6. API Integration Rules

- Use TanStack Query for server state.
- Do not store server data in global client state unless necessary.
- Use query keys consistently.
- Separate API functions from components.
- Handle loading, error, and empty states.
- Auth token handling must be centralized.

Example query key style:

```ts
['applications']
['applications', applicationId]
['dashboard', 'summary']
['activities', 'grass', year]
```

## 7. Type Rules

- Define shared frontend API response types in `src/types`.
- Keep enum values aligned with backend enum names.
- Do not invent frontend-only status values unless mapped clearly.

Application status values:

```ts
export type ApplicationStatus =
  | 'INTERESTED'
  | 'PLANNED'
  | 'WRITING'
  | 'APPLIED'
  | 'DOCUMENT_PASSED'
  | 'DOCUMENT_FAILED'
  | 'CODING_TEST'
  | 'INTERVIEW'
  | 'FINAL_PASSED'
  | 'FINAL_FAILED'
  | 'ON_HOLD';
```

## 8. Component Rules

- Keep page components focused on layout and data composition.
- Extract reusable UI into components.
- Do not put API calls directly inside deeply nested components.
- Prefer readable component names.

Recommended components:

```text
DashboardSummaryCard
UpcomingDeadlineList
ActivityGrass
ApplicationList
ApplicationCard
ApplicationStatusBadge
DeadlineBadge
ApplicationForm
StatusChangeSelect
```

## 9. Styling Rules

- Use Tailwind CSS.
- Keep the design clean and portfolio-friendly.
- Avoid excessive animations.
- Use consistent spacing and typography.
- Make mobile responsive only after desktop MVP is stable.

## 10. Frontend MVP Priority

Build in this order:

1. API client setup
2. Login / signup UI
3. Application list page
4. Application create form
5. Application detail page
6. Status change interaction
7. Dashboard summary cards
8. Upcoming deadlines
9. Activity grass UI
10. UI polish and empty states

## 11. Do Not Do

- Do not implement mock-only features without backend alignment.
- Do not add drag-and-drop before MVP completion.
- Do not build complex chart dashboards first.
- Do not add crawling UI before backend supports it.
- Do not duplicate backend business rules in the frontend as the source of truth.

## 12. Portfolio Emphasis

When writing frontend code or documentation, emphasize:

- User-centered dashboard design
- Server state management with TanStack Query
- Status-based filtering and sorting
- Activity grass visualization
- Clean API integration with Spring Boot backend
- Responsive and maintainable component structure

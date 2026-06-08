-- CreateEnum
CREATE TYPE "ApplicationStatus" AS ENUM ('INTERESTED', 'WRITING', 'APPLIED', 'INTERVIEW', 'FINAL_PASSED', 'REJECTED');

-- CreateEnum
CREATE TYPE "ActivityType" AS ENUM ('APPLICATION_CREATED', 'STATUS_CHANGED', 'APPLIED', 'MEMO_UPDATED');

-- CreateTable
CREATE TABLE "users" (
    "id" SERIAL NOT NULL,
    "email" TEXT NOT NULL,
    "name" TEXT,
    "passwordHash" TEXT,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "users_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "applications" (
    "id" SERIAL NOT NULL,
    "userId" INTEGER NOT NULL,
    "companyName" TEXT NOT NULL,
    "jobTitle" TEXT NOT NULL,
    "position" TEXT,
    "jobUrl" TEXT,
    "platform" TEXT,
    "status" "ApplicationStatus" NOT NULL DEFAULT 'INTERESTED',
    "deadline" TIMESTAMP(3),
    "appliedAt" TIMESTAMP(3),
    "memo" TEXT,
    "portfolioVersion" TEXT,
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "applications_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "activity_logs" (
    "id" SERIAL NOT NULL,
    "userId" INTEGER NOT NULL,
    "applicationId" INTEGER NOT NULL,
    "activityType" "ActivityType" NOT NULL,
    "activityDate" TIMESTAMP(3) NOT NULL,
    "fromStatus" "ApplicationStatus",
    "toStatus" "ApplicationStatus",
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "activity_logs_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE UNIQUE INDEX "users_email_key" ON "users"("email");

-- CreateIndex
CREATE INDEX "applications_userId_status_idx" ON "applications"("userId", "status");

-- CreateIndex
CREATE INDEX "applications_userId_deadline_idx" ON "applications"("userId", "deadline");

-- CreateIndex
CREATE INDEX "applications_userId_appliedAt_idx" ON "applications"("userId", "appliedAt");

-- CreateIndex
CREATE UNIQUE INDEX "applications_userId_jobUrl_key" ON "applications"("userId", "jobUrl");

-- CreateIndex
CREATE INDEX "activity_logs_userId_activityType_activityDate_idx" ON "activity_logs"("userId", "activityType", "activityDate");

-- CreateIndex
CREATE INDEX "activity_logs_applicationId_activityType_idx" ON "activity_logs"("applicationId", "activityType");

-- AddForeignKey
ALTER TABLE "applications" ADD CONSTRAINT "applications_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "activity_logs" ADD CONSTRAINT "activity_logs_userId_fkey" FOREIGN KEY ("userId") REFERENCES "users"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "activity_logs" ADD CONSTRAINT "activity_logs_applicationId_fkey" FOREIGN KEY ("applicationId") REFERENCES "applications"("id") ON DELETE CASCADE ON UPDATE CASCADE;

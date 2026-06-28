package com.applyhub.application.domain;

import com.applyhub.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Entity
@Table(name = "applications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String companyName;

    @Column(nullable = false, length = 100)
    private String position;

    @Column(length = 2048)
    private String jobUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private JobPlatform platform;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ApplicationStatus status;

    private LocalDate deadline;

    private LocalDateTime appliedAt;

    @Column(length = 2000)
    private String memo;

    @Column(length = 100)
    private String portfolioVersion;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Application(
            User user,
            String companyName,
            String position,
            String jobUrl,
            JobPlatform platform,
            LocalDate deadline,
            String memo,
            String portfolioVersion) {
        this.user = user;
        this.companyName = companyName;
        this.position = position;
        this.jobUrl = jobUrl;
        this.platform = platform;
        this.status = ApplicationStatus.INTERESTED;
        this.deadline = deadline;
        this.memo = memo;
        this.portfolioVersion = portfolioVersion;
    }
}

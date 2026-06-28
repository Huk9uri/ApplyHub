package com.applyhub.application.repository;

import com.applyhub.application.domain.Application;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findAllByUserId(Long userId);

    Optional<Application> findByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndJobUrl(Long userId, String jobUrl);
}

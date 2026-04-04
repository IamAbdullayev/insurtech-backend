package com.insurtech.backend.repository;

import com.insurtech.backend.domain.entity.ClaimEstimation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClaimEstimationRepository extends JpaRepository<ClaimEstimation, UUID> {
}

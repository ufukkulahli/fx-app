package com.openpayd.fx.data.repository;

import com.openpayd.fx.data.entity.FXRateCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCheckRepository extends JpaRepository<FXRateCache, Long> {
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer checkDBHealth();
}


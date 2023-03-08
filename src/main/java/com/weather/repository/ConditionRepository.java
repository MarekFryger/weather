package com.weather.repository;

import com.weather.domain.Condition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Condition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConditionRepository extends JpaRepository<Condition, Long> {}

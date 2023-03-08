package com.weather.repository;

import com.weather.domain.Current;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Current entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrentRepository extends JpaRepository<Current, Long> {}

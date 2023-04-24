package com.dhruza.physioconnectapi.repository;

import com.dhruza.physioconnectapi.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}

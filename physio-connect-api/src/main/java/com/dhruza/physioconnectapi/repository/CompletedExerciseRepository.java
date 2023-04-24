package com.dhruza.physioconnectapi.repository;

import com.dhruza.physioconnectapi.model.CompletedExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletedExerciseRepository extends JpaRepository<CompletedExercise, Long> {
}
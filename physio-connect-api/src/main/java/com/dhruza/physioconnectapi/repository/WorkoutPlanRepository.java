package com.dhruza.physioconnectapi.repository;

import com.dhruza.physioconnectapi.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

    Set<WorkoutPlan> findAllByPatientId(Long partientId);

    @Query("SELECT wp FROM WorkoutPlan wp " +
            "LEFT JOIN FETCH wp.exercises e " +
            "WHERE wp.patient.id = :patientId AND wp.isActive = true")
    Set<WorkoutPlan> findActivePlansByPatientId(Long patientId);

    @Query("SELECT wp FROM WorkoutPlan wp " +
            "JOIN FETCH wp.exercises e " +
            "WHERE wp.id = :id")
    Optional<WorkoutPlan> findByIdWithExercises(Long id);
}

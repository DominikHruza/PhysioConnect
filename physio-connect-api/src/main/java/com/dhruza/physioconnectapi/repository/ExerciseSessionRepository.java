package com.dhruza.physioconnectapi.repository;

import com.dhruza.physioconnectapi.model.ExerciseSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ExerciseSessionRepository extends JpaRepository<ExerciseSession, Long> {

    @Query("SELECT es, es.plan, es.plan.exercises FROM ExerciseSession es JOIN FETCH es.completedExercises " +
            "JOIN FETCH es.plan " +
            "JOIN FETCH es.plan.exercises " +
            "WHERE es.plan.patient.id = :patientId")
    Set<ExerciseSession> findByPatient(Long patientId);

    @Query("SELECT es, es.plan, es.plan.exercises FROM ExerciseSession es JOIN FETCH es.completedExercises " +
            "JOIN FETCH es.plan " +
            "JOIN FETCH es.plan.exercises " +
            "WHERE es.plan.id = :planId")
    Set<ExerciseSession> findByWorkoutPlan(Long planId);
}
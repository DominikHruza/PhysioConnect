package com.dhruza.physioconnectapi.services;

import com.dhruza.physioconnectapi.exception.DataNotFoundException;
import com.dhruza.physioconnectapi.model.*;
import com.dhruza.physioconnectapi.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class ExerciseSessionServiceImpl implements ExerciseSessionService {

    private final ExerciseSessionRepository exerciseSessionRepository;
    private final CompletedExerciseRepository completedExerciseRepository;
    private final PatientRepository patientRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final ExerciseRepository exerciseRepository;

    @Transactional(readOnly = true)
    public Set<ExerciseSession> getAll(Long patientId) {
        patientRepository
                .findById(patientId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Patient with id " + patientId + " does not exists!"));
        return exerciseSessionRepository.findByPatient(patientId);
    }

    @Transactional
    public ExerciseSession addNew(ExerciseSession session) {
        final WorkoutPlan plan = workoutPlanRepository
                .findById(session.getPlan().getId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Workout plan with id " + session.getPlan().getId() + " does not exists!"));

        session.setPlan(plan);

        session.getCompletedExercises().forEach(e -> {
            final Exercise plannedEx = exerciseRepository.findById(
                    e.getExercise().getId()).orElseThrow(
                    () -> new DataNotFoundException("Exercise with id "
                            + e.getExercise().getId() + " does not exists!"));
            e.setExercise(plannedEx);
            e.setPlan(plan);
            e.setSession(session);
        });

        return exerciseSessionRepository.save(session);
    }

    @Override
    public Set<ExerciseSession> getAllByPlan(Long planId) {
        workoutPlanRepository.findById(planId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Patient with id " + planId + " does not exists!"));
        return exerciseSessionRepository.findByWorkoutPlan(planId);
    }
}

package com.dhruza.physioconnectapi.services;

import com.dhruza.physioconnectapi.model.ExerciseSession;

import java.util.Set;

public interface ExerciseSessionService {
    Set<ExerciseSession> getAll(Long patientId);
    ExerciseSession addNew(ExerciseSession session);
    Set<ExerciseSession> getAllByPlan(Long planId);
}

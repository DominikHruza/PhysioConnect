package com.dhruza.physioconnectapi.services;

import com.dhruza.physioconnectapi.model.WorkoutPlan;

import java.util.Set;

public interface WorkoutPlanService {
    Set<WorkoutPlan> findAll(Long patientId);
    WorkoutPlan findAllByPlanId(Long planId);
    Set<WorkoutPlan> findAllActivePlans(Long patientId);
    WorkoutPlan savePlan(WorkoutPlan workoutPlan);
    WorkoutPlan updatePlan(WorkoutPlan workoutPlan, Long planId);
    void updateStatus(Long planId, Boolean isActive);
}

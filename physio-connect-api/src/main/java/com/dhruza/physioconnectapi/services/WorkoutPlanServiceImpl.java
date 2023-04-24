package com.dhruza.physioconnectapi.services;

import com.dhruza.physioconnectapi.exception.DataNotFoundException;
import com.dhruza.physioconnectapi.model.Exercise;
import com.dhruza.physioconnectapi.model.Patient;
import com.dhruza.physioconnectapi.model.Practitioner;
import com.dhruza.physioconnectapi.model.WorkoutPlan;
import com.dhruza.physioconnectapi.repository.ExerciseRepository;
import com.dhruza.physioconnectapi.repository.PatientRepository;
import com.dhruza.physioconnectapi.repository.PractitionerRepository;
import com.dhruza.physioconnectapi.repository.WorkoutPlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class WorkoutPlanServiceImpl implements WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private  final PatientRepository patientRepository;
    private final PractitionerRepository practitionerRepository;
    private final ExerciseRepository exerciseRepository;

    @Transactional(readOnly = true)
    public Set<WorkoutPlan> findAll(Long patientId){
        return workoutPlanRepository.findAllByPatientId(patientId);
    }

    @Transactional(readOnly = true)
    public WorkoutPlan findAllByPlanId(Long planId){
        return  workoutPlanRepository
                .findByIdWithExercises(planId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Plan with id " + planId + " does not exists!"));
    }

    @Transactional(readOnly = true)
    public Set<WorkoutPlan> findAllActivePlans(Long patientId) {
        return workoutPlanRepository.findActivePlansByPatientId(patientId);
    }

    @Transactional
    public WorkoutPlan savePlan(WorkoutPlan workoutPlan) {
        final Practitioner practitioner = getPractitioner(workoutPlan);
        final Patient patient = getPatient(workoutPlan);
        final Set<Exercise> exercises = workoutPlan.getExercises();

        workoutPlan.setPractitioner(practitioner);
        workoutPlan.setPatient(patient);
        workoutPlan.setExercises(Set.of());
        final WorkoutPlan newPlan = workoutPlanRepository.save(workoutPlan);

        exercises.forEach(e -> e.setPlan(newPlan));
        newPlan.setExercises(exercises);
        return newPlan;
    }

    @Transactional
    public WorkoutPlan updatePlan(WorkoutPlan updateData, Long planId) {
        final WorkoutPlan oldPlan = workoutPlanRepository
                .findByIdWithExercises(planId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Plan with id " + planId + " does not exists!"));

        oldPlan.setPractitioner(getPractitioner(updateData));
        oldPlan.setPatient(getPatient(updateData));
        oldPlan.setStartAt(updateData.getStartAt());
        oldPlan.setEndAt(updateData.getEndAt());
        oldPlan.setDescription(updateData.getDescription());
        oldPlan.setEffortLevel(updateData.getEffortLevel());
        oldPlan.setPainLevel(updateData.getPainLevel());
        oldPlan.setIsActive(updateData.getIsActive());

        updateData.getExercises().forEach(updateDataExercise -> {
            if(Objects.isNull(updateDataExercise.getId())) {
                oldPlan.addExercise(updateDataExercise);
                return;
            }

            final Exercise exrxFoundInDb = oldPlan.getExercises()
                    .stream()
                    .filter(oldPlanExercise -> oldPlanExercise.equals(updateDataExercise))
                    .findFirst()
                    .orElseThrow(() -> new DataNotFoundException("Exercise does does not exists on this plan!"));

            exrxFoundInDb.setExrxName(updateDataExercise.getExrxName());
            exrxFoundInDb.setExrxSets(updateDataExercise.getExrxSets());
            exrxFoundInDb.setExrxRepetition(updateDataExercise.getExrxRepetition());
            exrxFoundInDb.setVideoInstrUrl(updateDataExercise.getVideoInstrUrl());
            exrxFoundInDb.setDescription(updateDataExercise.getDescription());
            exrxFoundInDb.setEffortLevel(updateDataExercise.getEffortLevel());
            exrxFoundInDb.setPainLevel(updateDataExercise.getPainLevel());
            exrxFoundInDb.setPatientComment(updateDataExercise.getPatientComment());
            exrxFoundInDb.setIsCompleted(updateDataExercise.getIsCompleted());
        });

        final Set<Exercise> exercisesForRemoval = getExercisesForDeletion(updateData, oldPlan);
        if(!exercisesForRemoval.isEmpty()){
            exercisesForRemoval.forEach(e -> {
                exerciseRepository.delete(e);
                oldPlan.removeExercise(e);
            });
        }
        return oldPlan;
    }

   @Transactional
    public void updateStatus(Long planId, Boolean isActive) {
        final WorkoutPlan plan = workoutPlanRepository
                .findByIdWithExercises(planId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Plan with id " + planId + " does not exists!"));
        plan.setIsActive(isActive);
    }

    private Set<Exercise> getExercisesForDeletion(WorkoutPlan updateData, WorkoutPlan oldPlan) {
        return oldPlan.getExercises()
                .stream()
                .filter(oldPlanExercise -> !updateData.getExercises().contains(oldPlanExercise))
                .collect(Collectors.toSet());
    }

    private Practitioner getPractitioner(WorkoutPlan workoutPlan) {
        final Long practitionerId = workoutPlan.getPractitioner().getId();
        return practitionerRepository
                .findById(practitionerId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Practitioner with id " + practitionerId + " does not exists!"));

    }

    private Patient getPatient(WorkoutPlan workoutPlan) {
        final Long patientId = workoutPlan.getPatient().getId();
        return patientRepository
                .findById(patientId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Patient with id " + patientId + " does not exists!"));

    }
}

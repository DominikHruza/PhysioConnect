package com.dhruza.physioconnectapi.dto.mapping;

import com.dhruza.physioconnectapi.dto.AddCompletedExerciseRequest;
import com.dhruza.physioconnectapi.dto.CompletedExerciseResponse;
import com.dhruza.physioconnectapi.model.CompletedExercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompletedExerciseMapper {

    @Mapping(source = "completedExercise.exercise.id", target = "exerciseId")
    @Mapping(source = "completedExercise.exercise.exrxName", target = "name")
    CompletedExerciseResponse convertToDto(CompletedExercise completedExercise);

    @Mapping(source = "dto.exerciseId", target = "exercise.id")
    CompletedExercise convertFromDto(AddCompletedExerciseRequest dto);
}

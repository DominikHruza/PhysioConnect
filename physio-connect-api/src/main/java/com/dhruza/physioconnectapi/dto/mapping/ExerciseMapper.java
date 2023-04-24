package com.dhruza.physioconnectapi.dto.mapping;


import com.dhruza.physioconnectapi.dto.ExerciseDto;
import com.dhruza.physioconnectapi.model.Exercise;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    ExerciseDto convertToDto(Exercise exercise);

    //@Mapping(source = "dto.planId", target = "plan.id")
    Exercise convertFromDto(ExerciseDto dto);

    Set<Exercise> convertFromDto(Set<ExerciseDto> exerciseDtos);
}

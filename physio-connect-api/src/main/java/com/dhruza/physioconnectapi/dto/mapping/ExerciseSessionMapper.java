package com.dhruza.physioconnectapi.dto.mapping;

import com.dhruza.physioconnectapi.dto.AddExerciseSessionRequest;
import com.dhruza.physioconnectapi.dto.ExerciseSessionResponse;
import com.dhruza.physioconnectapi.model.ExerciseSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {CompletedExerciseMapper.class})
public interface ExerciseSessionMapper {

    @Mapping(source = "session.plan.id", target = "planId")
    Set<ExerciseSessionResponse> convertToDto(Set<ExerciseSession> session);

    @Mapping(source = "session.plan.id", target = "planId")
    ExerciseSessionResponse convertToDto(ExerciseSession session);

    @Mapping(source = "dto.planId", target = "plan.id")
    ExerciseSession convertFromDto(AddExerciseSessionRequest dto);
}

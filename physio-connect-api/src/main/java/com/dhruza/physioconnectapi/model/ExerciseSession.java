package com.dhruza.physioconnectapi.model;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "exercise_sessions")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExerciseSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    private WorkoutPlan plan;

    @OneToMany(mappedBy = "session", cascade = {CascadeType.ALL})
    private Set<CompletedExercise> completedExercises = new HashSet<>();

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    private String patientComment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExerciseSession that = (ExerciseSession) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return 2022;
    }

    public void addExercise(CompletedExercise exercise){
        this.completedExercises.add(exercise);
        exercise.setSession(this);
    }

    public void removeExercise(CompletedExercise exercise){
        this.completedExercises.remove(exercise);
        exercise.setSession(null);
    }
}
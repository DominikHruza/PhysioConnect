package com.dhruza.physioconnectapi.model;

import lombok.*;

import jakarta.persistence.*;

@Table(name = "completed_exercises")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CompletedExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    private WorkoutPlan plan;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne(optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private ExerciseSession session;

    private Integer completedSets;

    private Integer completedRepetition;

    private Integer effortLevel;

    private Integer painLevel;

    private String patientComment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompletedExercise that = (CompletedExercise) o;

        return getExercise().getId().equals(that.getExercise().getId());
    }

    @Override
    public int hashCode() {
        return 2022;
    }
}
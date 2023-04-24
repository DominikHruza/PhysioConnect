package com.dhruza.physioconnectapi.model;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Table(name = "workout_plans")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practitioner_id", nullable = false)
    private Practitioner practitioner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @OneToMany(mappedBy = "plan", cascade = {CascadeType.ALL})
    private Set<Exercise> exercises = new HashSet<>();

    @Column(name = "start_at", nullable = false)
    private LocalDate startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDate endAt;

    @Column(name = "description")
    private String description;

    @Column(name = "effort_level")
    private Integer effortLevel;

    @Column(name = "pain_level")
    private Integer painLevel;

    @Column(name = "is_active")
    private Boolean isActive;

    public void addExercise(Exercise exercise){
        this.exercises.add(exercise);
        exercise.setPlan(this);
    }

    public void removeExercise(Exercise exercise){
        this.exercises.remove(exercise);
        exercise.setPlan(null);
    }

    public void removePatients(){
        exercises.forEach(p -> p.setPlan(null));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutPlan that = (WorkoutPlan) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return 2022;
    }

    @Override
    public String toString() {
        return "WorkoutPlan{" +
                "id=" + id +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", description='" + description + '\'' +
                ", effortLevel=" + effortLevel +
                ", painLevel=" + painLevel +
                ", isActive=" + isActive +
                '}';
    }
}
package com.dhruza.physioconnectapi.model;

import lombok.*;

import jakarta.persistence.*;

@Table(name = "exercises")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private WorkoutPlan plan;

    @Column(name = "exrx_name", nullable = false)
    private String exrxName;

    @Column(name = "exrx_sets", nullable = false)
    private Integer exrxSets;

    @Column(name = "exrx_repetition", nullable = false)
    private Integer exrxRepetition;

    @Column(name = "video_instr_url")
    private String videoInstrUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "effort_level")
    private Integer effortLevel;

    @Column(name = "pain_level")
    private Integer painLevel;

    @Column(name = "patient_comment")
    private String patientComment;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        return getId() != null ? getId().equals(exercise.getId()) : false;
    }

    @Override
    public int hashCode() {
        return 2022;
    }
}
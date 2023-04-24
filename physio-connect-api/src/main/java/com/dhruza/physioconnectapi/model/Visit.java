package com.dhruza.physioconnectapi.model;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Table(name = "visits")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Visit {
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

    @Column(name = "scheduled_for")
    private LocalDateTime scheduledFor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Visit visit = (Visit) o;

        return getId() != null ? getId().equals(visit.getId()) : visit.getId() == null;
    }

    @Override
    public int hashCode() {
        return 2022;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", scheduledFor=" + scheduledFor +
                '}';
    }
}
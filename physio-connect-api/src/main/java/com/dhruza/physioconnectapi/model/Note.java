package com.dhruza.physioconnectapi.model;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Table(name = "notes")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practitioner_id", nullable = false)
    private Practitioner practitioner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt;

    @Column(name = "note_content", nullable = false)
    private String noteContent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        return getId() != null ? getId().equals(note.getId()) : note.getId() == null;
    }

    @Override
    public int hashCode() {
        return 2022;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", addedAt=" + addedAt +
                ", noteContent='" + noteContent + '\'' +
                '}';
    }
}
package com.dhruza.physioconnectapi.model;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Table(name = "documents")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    private String urlLocation;

    private LocalDateTime addedAt;

    private String fileName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        return getUrlLocation() != null ? getUrlLocation().equals(document.getUrlLocation()) : document.getUrlLocation() == null;
    }

    @Override
    public int hashCode() {
        return getUrlLocation() != null ? getUrlLocation().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", urlLocation='" + urlLocation + '\'' +
                ", addedAt=" + addedAt +
                '}';
    }
}
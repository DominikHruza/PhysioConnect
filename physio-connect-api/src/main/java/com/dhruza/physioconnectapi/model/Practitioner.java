package com.dhruza.physioconnectapi.model;

import com.dhruza.physioconnectapi.auth.model.AppUser;
import lombok.*;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "practitioners")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Practitioner extends AppUser {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private AppUser appUser;

    private String uid;

    @OneToMany(mappedBy = "practitioner", cascade = CascadeType.ALL)
    private Set<Patient> patients = new HashSet<>();

    @OneToMany(mappedBy = "practitioner", cascade = CascadeType.ALL)
    private Set<Visit> visits = new HashSet<>();

    public void addPatient(Patient patient){
        this.patients.add(patient);
        patient.setPractitioner(this);
    }

    public void removePatient(Patient patient){
        patient.setPractitioner(null);
        this.patients.remove(patient);
    }

    public void removePatients(){
        patients.forEach(p -> p.setPractitioner(null));
    }

    public void addVisit(Visit visit){
        this.visits.add(visit);
        visit.setPractitioner(this);
    }

    public void removeVisit(Visit visit){
        visit.setPractitioner(null);
        this.visits.remove(visit);
    }

    public void removeVisits(){
        visits.forEach(p -> p.setPractitioner(null));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Practitioner that = (Practitioner) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return 2022;
    }

    @Override
    public String toString() {
        return "Practitioner{" +
                "id=" + id +
                '}';
    }
}
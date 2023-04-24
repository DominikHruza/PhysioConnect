package com.dhruza.physioconnectapi.model;

import com.dhruza.physioconnectapi.auth.model.AppUser;
import lombok.*;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "patients")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends AppUser {
//    @Id
//    private Long id;
//
//    @OneToOne
//    @JoinColumn(name = "id")
//    @MapsId
//    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practitioner_id", nullable = false)
    private Practitioner practitioner;

    private String diagnosis;

    @Column(name = "reg_code")
    private String registrationCode;

    @Column(name = "reg_complete")
    private Boolean registrationComplete;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Note> notes = new HashSet<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Visit> visits = new HashSet<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<WorkoutPlan> plans = new HashSet<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Document> documents = new HashSet<>();

    public void addNote(Note note){
        this.notes.add(note);
        note.setPatient(this);
    }

    public void removeNote(Note note){
        note.setPatient(null);
        this.notes.remove(note);
    }

    public void removeNote(){
        notes.forEach(p -> p.setPatient(null));
    }

    public void addVisit(Visit visit){
        this.visits.add(visit);
        visit.setPatient(this);
    }

    public void removeVisit(Visit visit){
        visit.setPatient(null);
        this.visits.remove(visit);
    }

    public void removeVisits(){
        visits.forEach(p -> p.setPatient(null));
    }

    public void addWorkoutPlan(WorkoutPlan plan){
        this.plans.add(plan);
        plan.setPatient(this);
    }

    public void removeWorkoutPlan(WorkoutPlan plan){
        plan.setPatient(null);
        this.plans.remove(plan);
    }

    public void removeWorkoutPlans(){
        plans.forEach(p -> p.setPatient(null));
    }

    public void addDocument(Document document){
        this.documents.add(document);
        document.setPatient(this);
    }

    public void removeDocument(Document document){
        document.setPatient(null);
        this.documents.remove(document);
    }

    public void removeDocuments(){
        documents.forEach(p -> p.setPatient(null));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        return getId() != null ? getId().equals(patient.getId()) : patient.getId() == null;
    }

    @Override
    public int hashCode() {
        return 2022;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + getId() +
                ", diagnosis='" + diagnosis + '\'' +
                '}';
    }
}
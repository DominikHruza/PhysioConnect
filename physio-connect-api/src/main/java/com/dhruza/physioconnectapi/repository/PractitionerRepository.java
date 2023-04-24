package com.dhruza.physioconnectapi.repository;

import com.dhruza.physioconnectapi.model.Practitioner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PractitionerRepository extends JpaRepository<Practitioner, Long> {

}

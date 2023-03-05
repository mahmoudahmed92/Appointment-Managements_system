package com.stc.appointment.restapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.stc.appointment.restapi.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

	
}

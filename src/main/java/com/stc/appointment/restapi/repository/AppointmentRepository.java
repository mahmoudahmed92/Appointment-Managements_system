package com.stc.appointment.restapi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.stc.appointment.restapi.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

      @Query("Select t from Appointment t where t.appointmentTime=CURRENT_DATE")
	  List<Appointment> findByTodayAppointment();
	  List<Appointment> findByAppointmentTimeAndCanceled(LocalDateTime date,boolean canceled);
	  List<Appointment> findByPatientName(String patientName);
	  List<Appointment> findByAppointmentTimeBetween(LocalDateTime start, LocalDateTime end);
	  List<Appointment> findByPatientIdOrderByAppointmentTimeAsc(Long patientId);
	  List<Appointment> findByPatientNameContainingIgnoreCase(String patientName);

	  

}

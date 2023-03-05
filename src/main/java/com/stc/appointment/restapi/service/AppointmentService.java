package com.stc.appointment.restapi.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stc.appointment.restapi.model.Appointment;
import com.stc.appointment.restapi.repository.AppointmentRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	public Appointment addAppointment(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}

	public Appointment cancelAppointment(Appointment appointment) {
	    Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointment.getId());
	    if (appointmentOptional.isPresent()) {
	        Appointment appointment_Spare = appointmentOptional.get();
	        appointment_Spare.setCanceled(true);
	        appointment_Spare.setCancelReason(appointment.getCancelReason());
	        return appointmentRepository.save(appointment_Spare);
	    } else {
	        // handle the case where the appointment is not found
	    	return  null;
	    }
	}


	public List<Appointment> searchAppointmentsByName(String patientName) {

		return appointmentRepository.findByPatientNameContainingIgnoreCase(patientName);
		
	}

	public List<Appointment> searchAppointmentsByDate(LocalDateTime start, LocalDateTime end) {

		return appointmentRepository.findByAppointmentTimeBetween(start, end);

	}

	public List<Appointment> getAppointmentsForToday() {
		LocalDateTime start = LocalDateTime.now().with(LocalTime.MIN);
		LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);
		return appointmentRepository.findByAppointmentTimeBetween(start, end);
	}

	public List<Appointment> getPatientAppointmentHistory(Long patientId) {
		return appointmentRepository.findByPatientIdOrderByAppointmentTimeAsc(patientId);
	}
}
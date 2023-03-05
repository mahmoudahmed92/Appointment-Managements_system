package com.stc.appointment.restapi.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.stc.appointment.restapi.model.Appointment;
import com.stc.appointment.restapi.model.Patient;
import com.stc.appointment.restapi.service.AppointmentService;
import com.stc.appointment.restapi.service.PatientService;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private PatientService patientService;

	@PostMapping("{patientId}/createAppointment")
	public ResponseEntity<Appointment> addAppointment(@RequestBody Appointment appointment,@PathVariable Long patientId) {
		System.err.println(":::  AppointmentController.createAppointment :::");
	    try {
        	Optional<Patient> patient = patientService.getPatientById(patientId);
        	if (!patient.isPresent()) {
                return ResponseEntity.notFound().build();
            }
	    	
	    	appointment.setPatient(patient.get());
	    	Appointment savedAppointment = appointmentService.addAppointment(appointment);
	      return ResponseEntity.ok(savedAppointment);
	    } catch (Exception e) {
	      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	          .body(null);
	    }
	  }
	
	@PutMapping("/cancelAppointment")
	  public ResponseEntity<Appointment> cancelAppointment(
			  @RequestBody Appointment appointment) {
	    try {
	      Appointment cancelledAppointment = appointmentService.cancelAppointment(appointment);
	      if (cancelledAppointment==null) {
			return ResponseEntity.notFound().build();
		}
	      return ResponseEntity.ok(cancelledAppointment);
	    
	    } catch (Exception e) {
	      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	          .body(null);
	    }
	  }


	

	@GetMapping("/allTodayAppointments")
	  public ResponseEntity<List<Appointment>> getTodaysAppointments() {
	    try {
	      List<Appointment> appointments = appointmentService.getAppointmentsForToday();
	      if (appointments.isEmpty()) {
	            return ResponseEntity.notFound().build();
	        }
	      return ResponseEntity.ok(appointments);
	      
	    }
	    catch (Exception e) {
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	          .body(Collections.emptyList());
	    }
	  }
	
	 @GetMapping("/search/{patientName}")
	  public ResponseEntity<List<Appointment>> searchAppointments(@PathVariable("patientName") String patientName) {
	    try {
	      List<Appointment> appointments = appointmentService.searchAppointmentsByName( patientName);
	      if (appointments.isEmpty()) {
	            return ResponseEntity.notFound().build();
	        }
	      return ResponseEntity.ok(appointments);
	    } 
	    catch (Exception e) {
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	          .body(Collections.emptyList());
	    }
	  }
	 
	 @GetMapping("/searchDate")
	  public ResponseEntity<List<Appointment>> searchAppointmentByDate(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
		        @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
	    try {

	      List<Appointment> appointments = appointmentService.searchAppointmentsByDate(start,end);
	      if (appointments.isEmpty()) {
	            return ResponseEntity.notFound().build();
	        }
	      return ResponseEntity.ok(appointments);
	    } catch (Exception e) {
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	          .body(Collections.emptyList());
	    }
	  }
	 @GetMapping("/appointments/history/{patientId}")
	 public ResponseEntity<List<Appointment>> getPatientAppointmentHistory(@PathVariable Long patientId) {
		 try {

		     List<Appointment> appointments = appointmentService.getPatientAppointmentHistory(patientId);
		     if (appointments.isEmpty()) {
		            return ResponseEntity.notFound().build();
		        }
		     return ResponseEntity.ok(appointments);
		    } catch (Exception e) {
		      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		          .body(Collections.emptyList());
		    }

	 }

}

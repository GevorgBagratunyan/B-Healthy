package com.blueteam.bhealthy.controller;

import com.blueteam.bhealthy.entity.Hemodynamica;
import com.blueteam.bhealthy.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    //Receives patients hemodynamic parameters each 5 min
    @PutMapping("/{id}")
    public ResponseEntity<Void> trackHemodynamica(@RequestBody Hemodynamica hemodynamica, @PathVariable Long id) {
        patientService.trackHemodynamicParams(hemodynamica, id);
        return ResponseEntity.ok().build();
    }
}

package com.blueteam.bhealthy.service;

import com.blueteam.bhealthy.dto.PatientDTO;
import com.blueteam.bhealthy.entity.Hemodynamica;
import com.blueteam.bhealthy.entity.Patient;
import com.blueteam.bhealthy.repository.PatientRepository;
import com.blueteam.bhealthy.service.crud.CRUD;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PatientService implements CRUD<PatientDTO, Long> {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    @Override
    public void create(PatientDTO patientDTO) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public PatientDTO get(Long aLong) {
        return null;
    }

    @Override
    public void update(PatientDTO patientDTO) {

    }

    public void trackHemodynamicParams(Hemodynamica hemodynamica, Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        patient.addHemodynamicParameter(hemodynamica);
        patientRepository.save(patient);
    }
}

package com.gevbagratunyan.bhealthy.service;

import com.gevbagratunyan.bhealthy.dto.PatientDTO;
import com.gevbagratunyan.bhealthy.entity.Hemodynamica;
import com.gevbagratunyan.bhealthy.entity.Patient;
import com.gevbagratunyan.bhealthy.repository.PatientRepository;
import com.gevbagratunyan.bhealthy.service.crud.CRUD;
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

    public void observeHemodynamics(Hemodynamica hemodynamica, Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        patient.addHemodynamicParameter(hemodynamica);
        patientRepository.save(patient);
    }
}

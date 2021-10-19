package com.blueteam.bhealthy.entity;

import com.blueteam.bhealthy.entity.observer.Observer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctor")
public class Doctor implements Observer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "doctors_patients",
    joinColumns = @JoinColumn(name = "doctor_id"),
    inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private List<Patient> patients = new ArrayList<>();

    @Override
    public void handleEvent(Hemodynamica hemodynamica, Long patientId) {
        Integer heartRateAVG = hemodynamica.getHeartRate();
        Integer saturationAVG = hemodynamica.getSaturation();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime alertTime = LocalDateTime.now();
        System.err.println("Sending Email to doctor with hemodynamic parameters...");
        System.err.println("Heart Rate is: " + heartRateAVG + ", Saturation is: " + saturationAVG);
        System.err.println(dtf.format(alertTime));
    }
}

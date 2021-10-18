package com.gevbagratunyan.bhealthy.entity;

import com.gevbagratunyan.bhealthy.entity.observer.Observed;
import com.gevbagratunyan.bhealthy.entity.observer.Observer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "patient")
public class Patient implements Observed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(name = "patients_hemodynamica")
    private List<Hemodynamica> hemodynamicas = new ArrayList<>();

    @ManyToMany(mappedBy = "patients")
    private List<Doctor> doctors = new ArrayList<>();

    //Notifies all observing doctors when hemodynamic parameters are dangerous
    public void addHemodynamicParameter(Hemodynamica hemodynamica) {
        if(hemodynamicas.size()==10) {
            hemodynamicas.clear();
        }
        this.hemodynamicas.add(hemodynamica);
        if (isDangerous()) {
            notifyObservers();
        }
    }

    private void RedAlert() {
        notifyObservers();
    }


    private boolean isDangerous() {
        Hemodynamica hemodynamica = calculateAvgHemodynamica();
        Integer heartRateAVG = hemodynamica.getHeartRate();
        Integer saturationAVG = hemodynamica.getSaturation();

        return heartRateAVG > 90 || saturationAVG < 93;
    }

    @Override
    public void addObserver(Observer observer) {
        doctors.add((Doctor) observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        doctors.remove((Doctor) observer);
    }

    @Override
    public void notifyObservers() {
        for (Doctor d : doctors) {
            Hemodynamica avg = calculateAvgHemodynamica();
            d.handleEvent(avg, this.id );
        }
        hemodynamicas.clear();
    }

    private Hemodynamica calculateAvgHemodynamica() {
        Integer sumHR = 0;
        Integer sumSAT = 0;
        for (Hemodynamica h : this.hemodynamicas) {
            sumHR += h.getHeartRate();
            sumSAT += h.getSaturation();
        }
        Integer heartRateAVG = sumHR / this.hemodynamicas.size();
        Integer saturationAVG = sumSAT / this.hemodynamicas.size();

        return new Hemodynamica(saturationAVG, heartRateAVG);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Hemodynamica> getHemodynamicas() {
        return hemodynamicas;
    }

    public void setHemodynamicas(List<Hemodynamica> hemodynamica) {
        this.hemodynamicas = hemodynamica;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id) && Objects.equals(name, patient.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}

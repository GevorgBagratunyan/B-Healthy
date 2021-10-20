package com.blueteam.bhealthy.entity;

import com.blueteam.bhealthy.entity.observer.Observed;
import com.blueteam.bhealthy.entity.observer.Observer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "patient", indexes = @Index(name = "multiIndex", columnList = "name ASC, med_history ASC"))
public class Patient implements Observed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "med_history")
    private Long medHistoryNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Hemodynamica> hemodynamics = new ArrayList<>();

    @ManyToMany(mappedBy = "patients")
    private List<Doctor> doctors = new ArrayList<>();

    //Notifies all observing doctors when hemodynamic parameters are dangerous
    public void addHemodynamicParameter(Hemodynamica hemodynamica) {
        this.hemodynamics.add(hemodynamica);
        //Always keeps the list with 10 params, like queue FIFO
        if(hemodynamics.size()==11) {
            this.hemodynamics.remove(0);
        } else return;
        //if already collected 10 parameters, check danger level
        if (isDangerous()) {
            notifyObservers();
        }
    }

    private void RedAlert() {
        notifyObservers();
    }

    private boolean isDangerous() {
        Hemodynamica avgHemodynamica = calculateAvgHemodynamica();
        Integer heartRateAVG = avgHemodynamica.getHeartRate();
        Integer saturationAVG = avgHemodynamica.getSaturation();

        return heartRateAVG > 90 || heartRateAVG < 60 || saturationAVG < 93;
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
        Hemodynamica avg = calculateAvgHemodynamica();
        for (Doctor d : doctors) {
            d.handleEvent(avg, this.id );
        }
        hemodynamics.clear();
    }

    private Hemodynamica calculateAvgHemodynamica() {
        Integer sumHR = 0;
        Integer sumSAT = 0;
        for (Hemodynamica h : this.hemodynamics) {
            sumHR += h.getHeartRate();
            sumSAT += h.getSaturation();
        }
        Integer heartRateAVG = sumHR / this.hemodynamics.size();
        Integer saturationAVG = sumSAT / this.hemodynamics.size();

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

    public List<Hemodynamica> getHemodynamics() {
        return hemodynamics;
    }

    public void setHemodynamics(List<Hemodynamica> hemodynamica) {
        this.hemodynamics = hemodynamica;
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

package com.blueteam.bhealthy.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "hemodynamica")
public class Hemodynamica {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "saturation")
    private Integer saturation;

    @Column(name = "heart_rate")
    private Integer heartRate;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Hemodynamica() {
    }

    public Hemodynamica(Integer saturation, Integer heartRate) {
        this.saturation = saturation;
        this.heartRate = heartRate;
    }

    public Integer getSaturation() {
        return saturation;
    }

    public void setSaturation(Integer saturation) {
        this.saturation = saturation;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hemodynamica that = (Hemodynamica) o;
        return Objects.equals(id, that.id) && Objects.equals(saturation, that.saturation) && Objects.equals(heartRate, that.heartRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, saturation, heartRate);
    }
}

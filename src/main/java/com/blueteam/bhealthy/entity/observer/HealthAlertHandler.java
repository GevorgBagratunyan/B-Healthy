package com.blueteam.bhealthy.entity.observer;

import com.blueteam.bhealthy.entity.Hemodynamica;


public class HealthAlertHandler implements Observer{

    @Override
    public void handleEvent(Hemodynamica hemodynamica, Long patientId) {
        System.out.println("HEALTH IN DANGER, dangerous parameters in hemodynamica\n");
    }
}

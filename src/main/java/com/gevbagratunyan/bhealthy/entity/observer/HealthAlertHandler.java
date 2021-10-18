package com.gevbagratunyan.bhealthy.entity.observer;

import com.gevbagratunyan.bhealthy.entity.Hemodynamica;


public class HealthAlertHandler implements Observer{

    @Override
    public void handleEvent(Hemodynamica hemodynamica, Long patientId) {
        System.out.println("HEALTH IN DANGER, dangerous parameters in hemodynamica\n");
    }
}

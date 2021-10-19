package com.blueteam.bhealthy.entity.observer;

import com.blueteam.bhealthy.entity.Hemodynamica;

public interface Observer {

    void handleEvent(Hemodynamica hemodynamica, Long patientId);
}

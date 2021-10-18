package com.gevbagratunyan.bhealthy.entity.observer;

import com.gevbagratunyan.bhealthy.entity.Hemodynamica;

import java.util.List;

public interface Observer {

    void handleEvent(Hemodynamica hemodynamica, Long patientId);
}

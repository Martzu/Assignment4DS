package com.example.springdemo.repositories;

import com.example.springdemo.entities.Sensor;

public interface FactoryRepository {

    public HospitalUserRepository createHospitalUserRepository();

    public MedicationRepository createMedicationRepository();

    public MedicalPlanRepository createMedicalPlanRepository();

    public SensorRepository createSensorRepository();
}

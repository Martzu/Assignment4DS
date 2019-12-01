package com.example.springdemo.repositories;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FactoryRepositoryImplementation implements FactoryRepository {
    private final HospitalUserRepository hospitalUserRepository;

    private final MedicalPlanRepository medicalPlanRepository;

    private final MedicationRepository medicationRepository;

    private final SensorRepository sensorRepository;

    @Override
    public HospitalUserRepository createHospitalUserRepository() {
        return hospitalUserRepository;
    }

    @Override
    public MedicationRepository createMedicationRepository() { return medicationRepository; }

    @Override
    public MedicalPlanRepository createMedicalPlanRepository() {
        return medicalPlanRepository;
    }

    @Override
    public SensorRepository createSensorRepository() { return sensorRepository; }

}

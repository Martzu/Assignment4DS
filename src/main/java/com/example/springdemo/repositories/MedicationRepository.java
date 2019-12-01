package com.example.springdemo.repositories;

import com.example.springdemo.entities.MedicalPlan;
import com.example.springdemo.entities.Medication;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import java.util.List;

public interface MedicationRepository extends CRUDRepository<Medication>, Repository<Medication, Integer> {

    List<Medication> findMedicationsByMedicalPlans(List<MedicalPlan> plans);
}

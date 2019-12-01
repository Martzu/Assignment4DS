package com.example.springdemo.repositories;

import com.example.springdemo.entities.HospitalUser;
import com.example.springdemo.entities.MedicalPlan;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import java.util.List;


public interface MedicalPlanRepository extends CRUDRepository<MedicalPlan>, Repository<MedicalPlan, Integer> {

    List<MedicalPlan> findMedicalPlansByPatient(HospitalUser patient);


}

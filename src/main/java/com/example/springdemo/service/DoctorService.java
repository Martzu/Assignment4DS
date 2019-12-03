package com.example.springdemo.service;


import com.example.springdemo.entities.HospitalUser;
import com.example.springdemo.entities.MedicalPlan;
import com.example.springdemo.entities.Recommendation;
import com.example.springdemo.entities.Sensor;
import com.example.springdemo.repositories.FactoryRepository;
import com.example.springdemo.soap.DoctorRequest;
import com.example.springdemo.soap.DoctorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final FactoryRepository factoryRepository;

    public DoctorResponse viewActivityHistory(DoctorRequest doctorRequest)
    {
        DoctorResponse doctorResponse = new DoctorResponse();

        if(!doctorRequest.getSessionId().isEmpty())
        {
            Recommendation recommendation = new Recommendation(Integer.parseInt(doctorRequest.getPatientId()), doctorRequest.getRecommendation());
            Sensor sensor = factoryRepository.createSensorRepository().findById(Integer.parseInt(doctorRequest.getSessionId())).get();
            sensor.setNormal(false);
            factoryRepository.createSensorRepository().save(sensor);
            factoryRepository.createRecommendationRepository().save(recommendation);
        }
        else
        {
            if(doctorRequest.getMedicalPlanId().isEmpty())
            {
                factoryRepository.createSensorRepository().findSensorsByPatientId(Integer.parseInt(doctorRequest.getPatientId())).forEach(activity -> {
                    doctorResponse.getActivity().add(activity.toString());
                });
            }
            else
            {
                if(doctorRequest.getRecommendation().isEmpty())
                {
                    Optional<HospitalUser> patient = factoryRepository.createHospitalUserRepository().findById(Integer.parseInt(doctorRequest.getPatientId()));
                    if(patient.isPresent())
                    {
                        List<MedicalPlan> medicalPlanList = factoryRepository.createMedicalPlanRepository().findMedicalPlansByPatient(patient.get());
                        MedicalPlan medicalPlan = medicalPlanList.get(Integer.parseInt(doctorRequest.getMedicalPlanId()) - 1);
                        doctorResponse.setMedicalPlan(medicalPlan.getIntakeIntervals());
                    }
                }
                else
                {
                    Integer doctorId = Integer.parseInt(doctorRequest.getSessionId());
                    Integer patientId = Integer.parseInt(doctorRequest.getPatientId());
                    factoryRepository.createRecommendationRepository().save(new Recommendation(patientId, doctorId, doctorRequest.getRecommendation()));
                }

            }
        }


        return doctorResponse;
    }

}

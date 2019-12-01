package com.example.springdemo.service;


import com.example.springdemo.repositories.FactoryRepository;
import com.example.springdemo.soap.DoctorRequest;
import com.example.springdemo.soap.DoctorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final FactoryRepository factoryRepository;

    public DoctorResponse viewActivityHistory(DoctorRequest doctorRequest)
    {
        DoctorResponse doctorResponse = new DoctorResponse();

        factoryRepository.createSensorRepository().findSensorsByPatientId(Integer.parseInt(doctorRequest.getPatientId())).forEach(activity -> {
            doctorResponse.getActivity().add(activity.toString());
        });

        return doctorResponse;
    }

}

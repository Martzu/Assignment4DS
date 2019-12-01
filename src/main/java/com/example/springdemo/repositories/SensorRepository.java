package com.example.springdemo.repositories;

import com.example.springdemo.entities.Sensor;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface SensorRepository extends CRUDRepository<Sensor>, Repository<Sensor, Integer> {
    List<Sensor> findSensorsByPatientId(int patientId);
}

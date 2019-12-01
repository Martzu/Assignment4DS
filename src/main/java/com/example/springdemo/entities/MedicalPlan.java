package com.example.springdemo.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class MedicalPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private HospitalUser patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private HospitalUser doctor;

    public MedicalPlan(HospitalUser patient, HospitalUser doctor, List<Medication> medications, String timePeriod, String intakeIntervals)
    {
        this.patient = patient;
        this.doctor = doctor;
        this.medications = medications;
        this.timePeriod = timePeriod;
        this.intakeIntervals = intakeIntervals;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "medical_plan_medication",
        joinColumns = {@JoinColumn(name = "medication_id")},
        inverseJoinColumns = {@JoinColumn(name = "medication_plan_id")}
    )
    private List<Medication> medications = new ArrayList<>();

    private String timePeriod;

    private String intakeIntervals;//shouldnt it be present in many to many table

}

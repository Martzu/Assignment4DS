package com.example.springdemo.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String sideEffects;
    private String dosage;

    public Medication(String name, String sideEffects, String dosage)
    {
        this.name = name;
        this.sideEffects = sideEffects;
        this.dosage = dosage;
    }

    @ManyToMany(mappedBy = "medications")
    private List<MedicalPlan> medicalPlans;


}

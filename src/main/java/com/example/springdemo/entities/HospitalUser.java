package com.example.springdemo.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hospital_user")
public class HospitalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String gender;
    private String address;
    private String username;
    private String password;
    private String birthDate;
    private String role;

    public HospitalUser(String name, String gender, String address, String username, String password, String birthDate, String role, List<HospitalUser> patients)
    {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.username = username;
        this.password = password;
        this.role = role;
        this.birthDate = birthDate;
        this.patients = patients;
        this.patients.forEach(patient -> patient.getCaretakers().add(this));
    }

    public HospitalUser(String name, String gender, String address, String username, String password, String birthDate, String role)
    {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.role = role;
    }


    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "caretaker_patient",
            joinColumns = {@JoinColumn(name = "patient_id")},
            inverseJoinColumns = {@JoinColumn(name = "caretaker_id")}
    )
    private List<HospitalUser> caretakers = new ArrayList<>();

    @ManyToMany(mappedBy = "caretakers")
    private List<HospitalUser> patients;


    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "patient")
    private List<MedicalPlan> medicalPlansPatient;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "doctor")
    private List<MedicalPlan> medicalPlansDoctor;

    public String toString()
    {
        return this.name + " " + this.gender + " " + this.role;
    }

}

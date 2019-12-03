package com.example.springdemo.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer patientId;

    @Column(name = "startTime")
    private String start;

    @Column(name= "endTime")
    private String end;
    private String activity;

    private boolean normal;

    private boolean anomaly;

    public String toString()
    {
        return "id: " + id + " ,patientId: " + patientId + " ,startTime: " + start + " ,endTime: " + end + " ,activity: " + activity + " ,anomaly: " + anomaly;
    }


}

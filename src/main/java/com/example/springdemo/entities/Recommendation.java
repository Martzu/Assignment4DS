package com.example.springdemo.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer patientId;

    private String recommendation;

    public Recommendation(Integer patientId, String recommendation)
    {
        this.recommendation = recommendation;
        this.patientId = patientId;
    }


}

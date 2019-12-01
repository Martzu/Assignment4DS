package com.example.springdemo.repositories;

import com.example.springdemo.entities.HospitalUser;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface HospitalUserRepository extends CRUDRepository<HospitalUser>, Repository<HospitalUser, Integer> {

    List<HospitalUser> findByRole(String role);

    Optional<HospitalUser> findHospitalUserByUsernameAndPassword(String username, String password);

    List<HospitalUser> findHospitalUsersByCaretakers(List<HospitalUser> caretakers);

    HospitalUser findHospitalUserByNameAndRole(String name, String role);

    void removeHospitalUserById(Integer id);

}

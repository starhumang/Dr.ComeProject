package com.drcome.project.medical.repository;

import org.springframework.data.repository.CrudRepository;

import com.drcome.project.medical.domain.Hospital;

public interface HospitalRepository extends CrudRepository<Hospital, String>{

}

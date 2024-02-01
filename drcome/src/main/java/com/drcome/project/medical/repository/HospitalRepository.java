package com.drcome.project.medical.repository;

import org.springframework.data.repository.CrudRepository;

import com.drcome.project.admin.domain.Hospital;

public interface HospitalRepository extends CrudRepository<Hospital, String>{

}

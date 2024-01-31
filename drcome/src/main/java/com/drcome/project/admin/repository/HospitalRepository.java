package com.drcome.project.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drcome.project.admin.domain.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, String>{

	public List<Hospital> findByhospitalStatus(String hospitalStatus);
	
//	@Modifying
//	@Query("UPDATE hospital SET hospitalStatus == 'b2  ' WHERE hospitalId = :id")
//	void hospitalStatusUpdate(@Param("id")String hospitalId);
	
	
}


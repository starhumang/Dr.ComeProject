package com.drcome.project.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.drcome.project.admin.domain.Usertable;

@EnableJpaRepositories
public interface UsertableRepository extends JpaRepository<Usertable, String>, UsertableRepoCustom {
	
	public Page<Usertable> findByuserStatus(String ustatus, Pageable pageable);
}
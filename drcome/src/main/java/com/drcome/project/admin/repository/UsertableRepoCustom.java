package com.drcome.project.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.drcome.project.admin.domain.Usertable;

public interface UsertableRepoCustom {

	Page<Usertable> findByuserStatus(String ustatus, Pageable pageable);
}

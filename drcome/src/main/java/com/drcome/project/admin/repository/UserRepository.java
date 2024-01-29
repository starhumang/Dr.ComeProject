package com.drcome.project.admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.drcome.project.admin.domain.User;

public interface UserRepository extends CrudRepository<User, String>{

	public List<User> findAll(String userId);
}

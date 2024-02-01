package com.drcome.project.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drcome.project.Detailcode;
import com.drcome.project.common.repository.DetailCodeRepository;

@Service
public class DetailCodeService {
	
	 @Autowired
	    private DetailCodeRepository detailCodeRepository;

	    public List<Detailcode> getAllDetailCodes() {
	        return detailCodeRepository.findAll();
	    }

}

package com.drcome.project.common.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Detailcode {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String detailCode;
    private String codeName;
    private String mainCode;
    
}

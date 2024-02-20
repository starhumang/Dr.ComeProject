package com.drcome.project.admin.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.admin.domain.QHospital;
import com.drcome.project.admin.domain.QUsertable;
import com.drcome.project.admin.domain.Usertable;
import com.drcome.project.admin.repository.HospitalListRepoCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class HospitalRepositoryImpl extends QuerydslRepositorySupport implements HospitalListRepoCustom {

	@Autowired
	private JPAQueryFactory queryFactory;
	
	public HospitalRepositoryImpl() {
		super(QHospital.class);
	}

	@Override
	public Page<Hospital> findByhospitalStatus(String hstatus, Pageable pageable) {
		JPQLQuery<Hospital> query = queryFactory.selectFrom(QHospital.hospital)
                .where(eqStatus(hstatus));

       List<Hospital> hospital = this.getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(hospital, pageable, query.fetchCount());
	}
	
	private BooleanExpression eqStatus(String hstatus) {
        if (hstatus == null || hstatus.isEmpty()) {
            return null;
        } 
        
        System.out.println("hstatus=============="+hstatus);
        return QHospital.hospital.hospitalStatus.eq(hstatus);
    }

}

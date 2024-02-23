package com.drcome.project.admin.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.drcome.project.admin.domain.Pharmacy;
import com.drcome.project.admin.domain.QPharmacy;
import com.drcome.project.admin.repository.PharmacyRepoCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class PharmacyRepositoryImpl extends QuerydslRepositorySupport implements PharmacyRepoCustom {

	@Autowired
	private JPAQueryFactory queryFactory;
	
	public PharmacyRepositoryImpl() {
		super(QPharmacy.class);
	}

	@Override
	public Page<Pharmacy> findByhospitalStatus(String pstatus, Pageable pageable) {
		JPQLQuery<Pharmacy> query = queryFactory.selectFrom(QPharmacy.pharmacy)
                .where(epStatus(pstatus));

       List<Pharmacy> pharmacy = this.getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(pharmacy, pageable, query.fetchCount());
	}
	
	private BooleanExpression epStatus(String pstatus) {
        if (pstatus == null || pstatus.isEmpty()) {
            return null;
        } 
        
        return QPharmacy.pharmacy.pharmacyStatus.eq(pstatus);
    }

}

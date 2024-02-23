package com.drcome.project.admin.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.drcome.project.admin.domain.QUsertable;
import com.drcome.project.admin.domain.Usertable;
import com.drcome.project.admin.repository.UsertableRepoCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class UsertableRepositoryImpl extends QuerydslRepositorySupport implements UsertableRepoCustom{

	public UsertableRepositoryImpl() {
        super(QUsertable.class);
    }

    @Autowired
    private JPAQueryFactory queryFactory;

    @Override
    public Page<Usertable> findByuserStatus(String ustatus, Pageable pageable) {
//    	System.out.println("ustatus1=============="+ustatus);
        JPQLQuery<Usertable> query = queryFactory.selectFrom(QUsertable.usertable)
                .where(eqStatus(ustatus));

       List<Usertable> users = this.getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(users, pageable, query.fetchCount());
    }

    private BooleanExpression eqStatus(String ustatus) {
        if (ustatus == null || ustatus.isEmpty()) {
            return null;
        } 
        
//        System.out.println("ustatus2=============="+ustatus);
        return QUsertable.usertable.userStatus.eq(ustatus);
    }
	
}


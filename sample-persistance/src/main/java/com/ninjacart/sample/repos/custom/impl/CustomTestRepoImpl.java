package com.ninjacart.sample.repos.custom.impl;

import com.ninjacart.sample.entity.TestEntity;
import com.ninjacart.sample.repos.custom.CustomRepoBase;
import com.ninjacart.sample.repos.custom.CustomTestRepo;
import org.hibernate.SQLQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collections;
import java.util.List;

@Repository
public class CustomTestRepoImpl extends CustomRepoBase implements CustomTestRepo  {
    @Override
    public List<TestEntity> getAllKeysWithSomeCustomLogic() {
        NativeQuery query = currentSession().createNativeQuery("select * from test");
       return query.list();
    }
}

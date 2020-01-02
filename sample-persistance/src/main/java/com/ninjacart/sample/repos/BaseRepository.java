package com.ninjacart.sample.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@NoRepositoryBean
@Transactional(value = "coreTransactionManager")
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

}
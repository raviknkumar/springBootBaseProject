package com.ninjacart.sample.repos;

import com.ninjacart.sample.entity.TestEntity;
import com.ninjacart.sample.repos.custom.CustomTestRepo;

import java.util.List;

public interface TestRepo extends BaseRepository<TestEntity, Integer>, CustomTestRepo {

   List<TestEntity> findByTestKey(String key);

}
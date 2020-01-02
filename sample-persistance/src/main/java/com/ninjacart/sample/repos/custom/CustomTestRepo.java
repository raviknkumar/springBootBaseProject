package com.ninjacart.sample.repos.custom;

import com.ninjacart.sample.entity.TestEntity;

import java.util.List;

public interface CustomTestRepo {

   List<TestEntity> getAllKeysWithSomeCustomLogic();



}

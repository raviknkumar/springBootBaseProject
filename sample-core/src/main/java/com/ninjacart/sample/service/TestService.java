package com.ninjacart.sample.service;

import com.ninjacart.sample.entity.TestEntity;
import com.ninjacart.sample.repos.TestRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
public class TestService {

    @Autowired
    private TestRepo testRepo;

    public List<TestEntity> findTestByKey(String key) {
        return testRepo.findByTestKey(key);
    }

    @PostConstruct
    public void addKey() {
        TestEntity testEntity=new TestEntity();
        testEntity.setTestKey("test");
        testEntity.setTestKeyValue("test");
        testEntity.setActive(true);
        testEntity.setCreatedBy(1);
        testEntity.setUpdatedBy(1);
        testRepo.save(testEntity);
        log.info("Data saved properly. Your setup is fine");

        List<TestEntity> testEntities=  findTestByKey("test");
        log.info("Data Retrieved properly. Your setup is fine");

        testRepo.getAllKeysWithSomeCustomLogic();

        log.info("Custom repos are properly. Your setup is fine");
        log.info("If you see this message remove this code");



    }




}

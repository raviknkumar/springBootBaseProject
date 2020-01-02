package com.ninjacart.sample.entity;

import lombok.Data;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity(name = "test")
public class TestEntity extends BaseEntityIntID {

    @Column(name = "test_key")
    private String testKey;

    @Column(name = "test_value")
    private String testKeyValue;


}

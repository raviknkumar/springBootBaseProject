package com.ninjacart.sample.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntityTimeSeriesID extends BaseEntity<String> {

    @Id
    @GenericGenerator (name="ninjaIdGenerator", strategy="com.ninjacart.sample.id.NinjaIdGenerator")
    @GeneratedValue (generator = "ninjaIdGenerator")
    private String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}

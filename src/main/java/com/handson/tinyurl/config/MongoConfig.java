package com.handson.tinyurl.config;

import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "tinydb";
    }

    @Override
    public boolean autoIndexCreation() {
        return true;
    }
}

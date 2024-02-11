package com.example.postgreSql.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
public class ServiceInfoTwo implements ServiceInfo {

    String test = "Testing ServiceInfoTwo";

    @Override
    public String getTest() {
        return test;
    }
}

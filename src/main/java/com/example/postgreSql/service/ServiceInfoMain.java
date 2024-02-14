package com.example.postgreSql.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class ServiceInfoMain implements ServiceInfo{
    String test = "Testing ServiceInfoMain";

    @Override
    public String getTest(){
        return test;
    }
}

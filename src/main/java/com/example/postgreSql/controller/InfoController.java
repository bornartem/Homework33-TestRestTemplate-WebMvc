package com.example.postgreSql.controller;

import com.example.postgreSql.service.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    private final ServiceInfo serviceInfo;

    @Autowired
    public InfoController(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    @Value("${server.port}")
    private String port;

    @GetMapping("/port")
    public String port() {
        return port;
    }

    @GetMapping("getTest")
    public String getTest(){
        return serviceInfo.getTest();
    }
}

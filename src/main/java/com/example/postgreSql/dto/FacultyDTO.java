package com.example.postgreSql.dto;

import com.example.postgreSql.model.Faculty;
import com.example.postgreSql.model.Student;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class FacultyDTO {
    private long id;
    private String name;
    private String color;
}

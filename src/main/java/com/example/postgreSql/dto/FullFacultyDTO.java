package com.example.postgreSql.dto;

import com.example.postgreSql.model.Faculty;
import com.example.postgreSql.model.Student;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class FullFacultyDTO {
    private long id;
    private String name;
    private String color;
    private StudentExamDTO student;

    public static FullFacultyDTO from(Faculty faculty) {
        FullFacultyDTO fullFacultyDTO = new FullFacultyDTO();
        fullFacultyDTO.setId(faculty.getId());
        fullFacultyDTO.setName(faculty.getName());
        fullFacultyDTO.setColor(fullFacultyDTO.getColor());

//      ???
        return fullFacultyDTO;
    }
}

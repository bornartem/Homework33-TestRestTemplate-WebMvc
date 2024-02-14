package com.example.postgreSql.dto;

import com.example.postgreSql.model.Student;
import lombok.Data;

@Data
public class FullStudentDTO {
    private long id;
    private String name;
    private int age;
    private FacultyDTO faculty;

    public static FullStudentDTO from(Student student) {
        FullStudentDTO studentDTO = new FullStudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setAge(student.getAge());

        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setId(student.getFaculty().getId());
        facultyDTO.setName(student.getFaculty().getName());
        facultyDTO.setColor(student.getFaculty().getColor());

        studentDTO.setFaculty(facultyDTO);
        return studentDTO;
    }
}

package com.example.postgreSql.service;


import com.example.postgreSql.model.Faculty;
import com.example.postgreSql.model.Student;
import com.example.postgreSql.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student readStudent(long id) {

        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }

    public Collection<Student> findStudentByAgeBetween(int age, int age1) {
        return studentRepository.findStudentByAgeBetween(age, age1);
    }

    public Faculty findFacultyByStudent(long id) {
        return studentRepository.getReferenceById(id).getFaculty();
    }
}

package com.example.postgreSql.service;

import com.example.postgreSql.model.Faculty;
import com.example.postgreSql.model.Student;
import com.example.postgreSql.repositories.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty readFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> facultyByColor(String color) {
        return facultyRepository.findFacultyByColor(color);
    }

    public Collection<Student> findStudentByFaculty(long id) {
        return facultyRepository.getReferenceById(id).getStudents();
    }
}

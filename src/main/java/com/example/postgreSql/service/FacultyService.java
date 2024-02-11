package com.example.postgreSql.service;

import com.example.postgreSql.dto.FullFacultyDTO;
import com.example.postgreSql.model.Faculty;
import com.example.postgreSql.model.Student;
import com.example.postgreSql.repositories.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository, RestTemplate restTemplate) {
        this.facultyRepository = facultyRepository;
        this.restTemplate = restTemplate;
    }

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public Faculty createFaculty(Faculty faculty) {
        if (facultyRepository.findAll().contains(faculty)) {
            logger.error("Such faculty " + faculty.getName() + " already exist");
            throw new RuntimeException();
        } else {
            logger.info("Was invoked method for create faculty");
            facultyRepository.save(faculty);
        }
        return faculty;
    }

    public Faculty readFaculty(long id) {
        logger.info("Was invoked method for read faculty");
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAll() {
        logger.info("Was invoked method for getAll faculty");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> facultyByColor(String color) {
        logger.info("Was invoked method for facultyByColor");
        return facultyRepository.findFacultyByColor(color);
    }

    public Collection<Student> findStudentByFaculty(long id) {
        logger.info("Was invoked method for findStudentByFaculty");
        return facultyRepository.getReferenceById(id).getStudents();
    }

    public FullFacultyDTO fullFacultyDTO(Long facultyId) {
        logger.debug("Was invoked method for fullFacultyDTO");
        Optional<Faculty> byId = facultyRepository.findById(facultyId);
        return byId
                .map(faculty -> FullFacultyDTO.from(faculty))
                .orElse(null);

    }
}

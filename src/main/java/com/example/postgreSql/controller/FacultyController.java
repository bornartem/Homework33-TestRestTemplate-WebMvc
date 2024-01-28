package com.example.postgreSql.controller;

import com.example.postgreSql.model.Faculty;
import com.example.postgreSql.model.Student;
import com.example.postgreSql.service.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> readFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.readFaculty(id);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping("/post")
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.updateFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color")
    public Collection<Faculty> getFacultyByColor(@RequestParam("color") String color) {
        return facultyService.facultyByColor(color);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getFaculties() {
        return ResponseEntity.ok(facultyService.getAll());
    }

    @GetMapping("/findByStudent{id}")
    public ResponseEntity<Collection<Student>> findStudentByFaculty(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.findStudentByFaculty(id));
    }
}

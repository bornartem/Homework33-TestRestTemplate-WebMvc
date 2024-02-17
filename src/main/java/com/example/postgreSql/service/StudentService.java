package com.example.postgreSql.service;


import com.example.postgreSql.dto.FullStudentDTO;
import com.example.postgreSql.model.Faculty;
import com.example.postgreSql.model.Student;
import com.example.postgreSql.repositories.StudentRepository;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@NoArgsConstructor
public class StudentService {
    private StudentRepository studentRepository;
    private RestTemplate restTemplate;

    @Autowired
    public StudentService(StudentRepository studentRepository, RestTemplate restTemplate) {
        this.studentRepository = studentRepository;
        this.restTemplate = restTemplate;
    }

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student createStudent(Student student) {
        if (studentRepository.findAll().contains(student)) {
            logger.error("Student with id " + student.getId() + " already added");
            throw new RuntimeException();
        } else {
            logger.info("Was invoked method for create student");
            studentRepository.save(student);
        }
        return student;
    }

    public Student readStudent(long id) {
        logger.info("Sent request for invoked method for read student");
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            logger.info("Was invoked method for read with student " + id);
        } else {
            logger.error("Not found student " + id);
        }
        return student.get();
    }

    public Student updateStudent(Student student) {
        logger.info("Was invoked method for update student");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            logger.info("Student successfully deleted " + id);
        } else {
            logger.error("Student not found " + id);
            throw new RuntimeException();
        }
    }

    public Collection<Student> getAll() {
        if (studentRepository.findAll().isEmpty()) {
            logger.error("List is empty");
            throw new RuntimeException();
        }
        logger.info("Was invoked method for getAll students");
        return studentRepository.findAll();
    }

    public Collection<Student> findStudentByAgeBetween(int age, int age1) {
        Collection<Student> studentAge = studentRepository.findStudentByAgeBetween(age, age1);
        if (studentAge.isEmpty()) {
            logger.error("Empty list");
        } else {
            logger.info("Was invoked method for findStudentByAgeBetween");
        }
        return studentAge;
    }

    public Faculty findFacultyByStudent(long id) {
        logger.info("Was invoked method for findFacultyByStudent");
        return studentRepository.getReferenceById(id).getFaculty();
    }

    public int findCountAllStudents() {
        logger.info("Was invoked method for findCountAllStudents");
        return studentRepository.getCountStudents();
    }

    public double getAverageAge() {
        logger.info("Was invoked method for getAverageAge");
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastStudents() {
        logger.info("Was invoked method for getLastStudents");
        return studentRepository.getLastStudents();
    }

    public FullStudentDTO fullStudent(Long studentId) {
        logger.info("Was invoked method for fullStudent");
        Optional<Student> byId = studentRepository.findById(studentId);
        return byId
                .map(student -> FullStudentDTO.from(student))
                .orElse(null);
    }

    public Collection<String> getAllStudentsNames() {
        return studentRepository.findAll()
                .stream()
                .map(student -> student.getName().toUpperCase())
                .filter(s -> s.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAvgAgeByStream() {
        return studentRepository.findAll()
                .parallelStream()
                .collect(Collectors.averagingInt(Student::getAge));
    }

    public boolean checkAvgAge() {
        if (getAverageAge() == getAvgAgeByStream()) {
            return true;
        } else {
            throw new RuntimeException();
        }
    }

    public int testInteger() {
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);

        return sum;
    }

    public List<String> getNamesStudentForThread() {
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .collect(Collectors.toList());
    }

    public synchronized List<String> synchronizedNamesStudentForThread() {
        List<String> studentsList1 = getNamesStudentForThread();
        return studentsList1;
    }

    public void printParallel() {
        List<String> studentsList = getNamesStudentForThread();
        System.out.println(studentsList.get(0));
        System.out.println(studentsList.get(1));
        new Thread(() -> {
            System.out.println(studentsList.get(2));
            System.out.println(studentsList.get(3));
        }).start();
        new Thread(() -> {
            System.out.println(studentsList.get(4));
            System.out.println(studentsList.get(5));
        }).start();
    }

    public synchronized void printSynchronized() {
        List<String> synchronizedList = synchronizedNamesStudentForThread();

        System.out.println(synchronizedList.get(0));
        System.out.println(synchronizedList.get(1));
        new Thread(() -> {
            System.out.println(synchronizedList.get(2));
            System.out.println(synchronizedList.get(3));
        }).start();
        new Thread(() -> {
            System.out.println(synchronizedList.get(4));
            System.out.println(synchronizedList.get(5));
        }).start();
    }
}

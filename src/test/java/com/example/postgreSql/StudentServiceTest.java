package com.example.postgreSql;

import com.example.postgreSql.model.Student;
import com.example.postgreSql.repositories.StudentRepository;
import com.example.postgreSql.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepositoryMock;

    @InjectMocks
    private StudentService studentService;
    private Student student = new Student(1L, "Artem", 25, null);
    private Student student1 = new Student(2L, "Art", 20, null);

    @Test
    public void shouldReturnCreateStudent() {
        when(studentRepositoryMock.save(student)).thenReturn(student);
        Student result = studentService.createStudent(student);
        assertEquals(student, result);
    }

    @Test
    public void shouldReturnReadStudent() {
        when(studentRepositoryMock.findById(1L)).thenReturn(Optional.of(student));
        Student result = studentService.readStudent(student.getId());
        assertEquals(student, result);
    }

    @Test
    public void shouldReturnUpdateStudent() {
        when(studentRepositoryMock.save(student)).thenReturn(student);
        Student result = studentService.updateStudent(student);
        assertEquals(student, result);
    }

    @Test
    public void shouldReturnGetAll() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student1);
        when(studentRepositoryMock.findAll()).thenReturn((List<Student>) students);
        Collection<Student> result = studentRepositoryMock.findAll();
        assertEquals(students, result);
    }
}

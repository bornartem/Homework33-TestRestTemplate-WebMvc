package com.example.postgreSql.controller_test;

import com.example.postgreSql.controller.FacultyController;
import com.example.postgreSql.controller.StudentController;
import com.example.postgreSql.model.Faculty;
import com.example.postgreSql.model.Student;
import com.example.postgreSql.repositories.AvatarRepository;
import com.example.postgreSql.repositories.FacultyRepository;
import com.example.postgreSql.repositories.StudentRepository;
import com.example.postgreSql.service.AvatarService;
import com.example.postgreSql.service.FacultyService;
import com.example.postgreSql.service.StudentService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @SpyBean
    private AvatarService avatarService;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private FacultyService facultyService;
    @InjectMocks
    private FacultyController facultyController;
    @InjectMocks
    private StudentController studentController;

    @Test
    void createStudentTest() throws Exception {
        Long id = 10L;
        String name = "Test";
        int age = 1;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student/post")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void readStudentTest() throws Exception {
        Long id = 10L;
        String name = "Test";
        int age = 1;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void deleteStudentTest() throws Exception {
        Long id = 10L;
        String name = "Test";
        int age = 1;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateStudentTest() throws Exception {
        Long id = 10L;
        String name = "Test";
        int age = 1;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student/update")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void getStudentTest() throws Exception {
        List<Student> students = new ArrayList<>(List.of(
                new Student(1L, "test", 1, null),
                new Student(2L, "test1", 2, null)
        ));
        when(studentRepository.findAll()).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void filterByAgeBetweenTest() throws Exception {
        List<Student> students = new ArrayList<>(List.of(
                new Student(1L, "test", 1, null),
                new Student(2L, "test2", 2, null),
                new Student(3L, "test3", 3, null),
                new Student(4L, "test4", 4, null)
        ));
        when(studentRepository.findStudentByAgeBetween(2, 4)).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age?min=" + 2 + "&max=" + 4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(jsonPath("$[3].id").value(4L));
    }

    @Test
    void findFacultyByStudentIDTest() throws Exception {
        Faculty faculty = new Faculty(1L, "test", "test1", null);
        Student student = new Student(1L, "test", 1, faculty);

        when(studentRepository.getReferenceById(1L)).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/findByFaculty" + student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void findCountAllStudents() throws Exception {
        Student student = new Student(1L, "Test", 1, null);
        Student student1 = new Student(2L, "Test2", 2, null);
        Integer count = 2;
        when(studentRepository.save(any(Student.class))).thenReturn(student, student1);
        when(studentRepository.getCountStudents()).thenReturn(count);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/count"))
                .andExpect(status().isOk());
    }
}

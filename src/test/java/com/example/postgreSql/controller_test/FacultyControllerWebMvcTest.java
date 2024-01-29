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

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class FacultyControllerWebMvcTest {

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
    void createFacultyTest() throws Exception {
        Long id = 10L;
        String name = "Test";
        String color = "test1";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty/post")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void readFacultyTest() throws Exception {
        Long id = 10L;
        String name = "Test";
        String color = "test1";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void deleteFacultyTest() throws Exception {
        Long id = 10L;
        String name = "Test";
        String color = "test1";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateFacultyTest() throws Exception {
        Long id = 10L;
        String name = "Test";
        String color = "test1";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void getFacultyByColorTest() throws Exception {
        Faculty faculty = new Faculty(1L, "Test", "white", null);
        when(facultyRepository.findFacultyByColor("white")).thenReturn(List.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/color?color=white")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test"))
                .andExpect(jsonPath("$[0].color").value("white"));
    }

    @Test
    void getFacultiesTest() throws Exception {
        List<Faculty> faculties = new ArrayList<>(List.of(
                new Faculty(1L, "test", "test1", null),
                new Faculty(2L, "test2", "test3", null)
        ));
        when(facultyRepository.findAll()).thenReturn(faculties);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }


    @Test
    void findStudentByFacultyTest() throws Exception {

        final List<Student> studentList = new ArrayList<>(List.of(
                new Student(1L, "Test", 1, null),
                new Student(2L, "Test2", 2, null)
        ));
        Faculty faculty = new Faculty(1L, "Test", "white", studentList);

        when(facultyRepository.getReferenceById(1L)).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/findByStudent" + faculty.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test"))
                .andExpect(jsonPath("$[0].age").value(1));
    }


}

package com.example.postgreSql.controller_test;

import com.example.postgreSql.controller.FacultyController;
import com.example.postgreSql.controller.StudentController;
import com.example.postgreSql.model.Faculty;
import com.example.postgreSql.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(facultyController).isNotNull();
    }

    @Test
    void readFacultyTest() throws Exception {
        Faculty faculty = new Faculty(5L, "Test", "Test1", null);
        restTemplate.postForObject("http://localhost:" + port + "/faculty/post", faculty, Faculty.class);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + faculty.getId(), Faculty.class))
                .isEqualTo(faculty);
        assertThat(faculty).isNotNull();
    }

    @Test
    void getFacultyTest() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotEmpty();
    }

    @Test
    void createFacultyTest() throws Exception {
        Faculty faculty = new Faculty(5L, "Test", "Test1", null);
        var result = restTemplate.postForObject("http://localhost:" + port + "/faculty/post", faculty, Faculty.class);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Test");
    }

    @Test
    void updateFacultyTest() throws Exception {
        Faculty faculty = new Faculty(5L, "Test", "Test1", null);
        restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        Faculty updated = new Faculty(5L, "Person", "test", null);
        ResponseEntity<Faculty> tested = restTemplate.exchange("http://localhost:" + port + "/faculty",
                HttpMethod.PUT,
                new HttpEntity<>(updated),
                Faculty.class);
        assertThat(tested.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(tested.getBody().getColor()).isEqualTo(updated.getColor());
    }

    @Test
    void deleteFacultyTest() throws Exception {
        Faculty faculty = new Faculty(5L, "Test", "Test1", null);
        restTemplate.postForObject("http://localhost:" + port + "/faculty/post", faculty, Faculty.class);
        ResponseEntity<Faculty> response = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(faculty),
                Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findStudentByFaculty() throws Exception {
        Faculty faculty = new Faculty(5L, "asdec", "dweq", null);
        restTemplate.postForObject("http://localhost:" + port + "/faculty/post", faculty, Faculty.class);
        Student student = new Student(61L, "Test2", 14, faculty);
        restTemplate.postForObject("http://localhost:" + port + "/student/post", student, Student.class);

        ResponseEntity<String> tested = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/findByStudent" + faculty.getId(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                String.class);
        assertThat(tested.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(tested.getBody()).isNotNull();
        assertThat(tested.getBody().contains(student.getName()));
    }

    @Test
    void getFacultyByColor(){
        Faculty faculty = new Faculty(5L, "asdec", "dweq", null);
        restTemplate.postForObject("http://localhost:" + port + "/faculty/post", faculty, Faculty.class);
        var tested = restTemplate.getForObject("http://localhost:" + port + "/faculty/color" + faculty.getColor(), String.class);

        assertThat(tested.contains(faculty.getColor()));
    }
}

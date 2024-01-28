package com.example.postgreSql;

import com.example.postgreSql.model.Faculty;
import com.example.postgreSql.repositories.FacultyRepository;
import com.example.postgreSql.service.FacultyService;
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
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {
    @Mock
    private FacultyRepository facultyRepositoryMock;

    @InjectMocks
    private FacultyService facultyService;
    private Faculty faculty = new Faculty(1L, "Shipbuilding", "Blue", null);
    private Faculty faculty1 = new Faculty(2L, "AirCrafting", "White", null);


    @Test
    public void shouldReturnCreateFaculty() {
        when(facultyRepositoryMock.save(faculty)).thenReturn(faculty);
        Faculty result = facultyService.createFaculty(faculty);
        assertEquals(faculty, result);
    }

    @Test
    public void shouldReturnReadFaculty() {
        when(facultyRepositoryMock.findById(1L)).thenReturn(Optional.of(faculty));
        Faculty result = facultyService.readFaculty(faculty.getId());
        assertEquals(faculty, result);
    }

    @Test
    public void shouldReturnUpdateFaculty() {
        when(facultyRepositoryMock.save(faculty)).thenReturn(faculty);
        Faculty result = facultyService.updateFaculty(faculty);
        assertEquals(faculty, result);
    }

    @Test
    public void shouldReturnGetAll() {
        Collection<Faculty> faculties = new ArrayList<>();
        faculties.add(faculty);
        faculties.add(faculty1);
        when(facultyRepositoryMock.findAll()).thenReturn((List<Faculty>) faculties);
        Collection<Faculty> result = facultyRepositoryMock.findAll();
        assertEquals(faculties, result);
    }

    @Test
    public void shouldReturnFacultyByColor() {
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(faculty);
        faculties.add(faculty1);
        when(facultyRepositoryMock.findFacultyByColor("Blue")).thenReturn(faculties);
        Collection<Faculty> result = facultyService.facultyByColor(faculty.getColor());
        assertIterableEquals(faculties, result);
    }
}

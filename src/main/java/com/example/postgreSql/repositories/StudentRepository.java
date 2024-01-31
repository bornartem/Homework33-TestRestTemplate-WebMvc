package com.example.postgreSql.repositories;

import com.example.postgreSql.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentByAgeBetween(int age, int age1);

    @Query(value = "SELECT COUNT (*) FROM STUDENT", nativeQuery = true)
    int getCountStudents();
    @Query(value = "select avg(age) from student", nativeQuery = true)
    double getAverageAge();
    @Query(value = "select * from student offset 3", nativeQuery = true)
    List<Student> getLastStudents();
}
package com.example.postgreSql.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"students"})
public class Faculty {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty")
    @JsonManagedReference
    private Collection<Student> students;
}
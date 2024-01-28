package com.example.postgreSql.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Avatar {
    @Id
    @GeneratedValue
    private long id;

    private String filePath;
    private long fileSize;
    private String mediaType;
    @Lob
    private byte[] data;

    @OneToOne
    private Student student;
}

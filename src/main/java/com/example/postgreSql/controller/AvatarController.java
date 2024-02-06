package com.example.postgreSql.controller;

import com.example.postgreSql.model.Avatar;
import com.example.postgreSql.service.AvatarService;
import com.example.postgreSql.service.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@RestController
public class AvatarController {
    private final AvatarService avatarService;
    private final StudentService studentService;

    @Autowired
    public AvatarController(AvatarService avatarService, StudentService studentService) {
        this.avatarService = avatarService;
        this.studentService = studentService;
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable long studentId, @RequestParam MultipartFile avatar) throws IOException {
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable long id) {
        Optional<Avatar> avatar = avatarService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.get().getMediaType()));
        headers.setContentLength(avatar.get().getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.get().getData());
    }

    @GetMapping("{id}/avatar-from-file")
    public void downloadAvatar(@PathVariable long id, HttpServletResponse response) throws IOException {
        Optional<Avatar> avatar = avatarService.findAvatar(id);

        Path path = Path.of(avatar.get().getFilePath());

        try (InputStream input = Files.newInputStream(path);
             OutputStream output = response.getOutputStream();) {
            response.setContentType(avatar.get().getMediaType());
            response.setContentLength((int) avatar.get().getFileSize());
            input.transferTo(output);
        }
    }
    @GetMapping("/get-page")
    public ResponseEntity<List<Avatar>> getAllAvatars(@RequestParam ("page") Integer page, @RequestParam("size") Integer size){
        List<Avatar> avatars = avatarService.getAllAvatars(page, size);
        return ResponseEntity.ok(avatars);
    }
}


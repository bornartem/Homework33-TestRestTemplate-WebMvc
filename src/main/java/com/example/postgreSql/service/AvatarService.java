package com.example.postgreSql.service;


import com.example.postgreSql.model.Avatar;
import com.example.postgreSql.model.Student;
import com.example.postgreSql.repositories.AvatarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    @Autowired
    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Value("${avatar.dir.path}")
    private String avatarDir;

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = studentService.readStudent(studentId);

        Path filePath = Path.of(avatarDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream input = file.getInputStream();
             OutputStream output = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream buffInput = new BufferedInputStream(input, 1024);
             BufferedOutputStream buffOutput = new BufferedOutputStream(output, 1024);
        ) {
            buffInput.transferTo(buffOutput);
        }
        Avatar avatar = findAvatar(studentId).orElseGet(Avatar::new);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateImagePreview(filePath));

        avatarRepository.save(avatar);
    }

    public Optional<Avatar> findAvatar(long studentId) {
        return avatarRepository.findByStudentId(studentId);
    }

    public byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream input = Files.newInputStream(filePath);
             BufferedInputStream buffIn = new BufferedInputStream(input, 1024);
             ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(buffIn);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), byteOut);
            return byteOut.toByteArray();
        }
    }

    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<Avatar> getAllAvatars(Integer num, Integer size) {
        PageRequest pageRequest = PageRequest.of(num - 1, size);
        Page<Avatar> pagedResult = avatarRepository.findAll(pageRequest);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Avatar>();
        }
    }
}

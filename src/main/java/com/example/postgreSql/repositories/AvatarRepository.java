package com.example.postgreSql.repositories;

import com.example.postgreSql.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);
}
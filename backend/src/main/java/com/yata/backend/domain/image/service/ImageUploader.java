package com.yata.backend.domain.image.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploader {
    String uploadImage(MultipartFile file, String email) throws IOException;
    String[] uploadImage(MultipartFile file ) throws IOException;
}

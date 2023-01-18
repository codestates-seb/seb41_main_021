package com.yata.backend.domain.uploadfile.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface Uploader {

    String[] upload(MultipartFile file) throws IOException;
}

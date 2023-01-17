package com.yata.backend.domain.image.controller;

import com.yata.backend.domain.image.service.ImageUploader;
import com.yata.backend.global.response.SingleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    private final ImageUploader imageUploader;

    public ImageController(ImageUploader imageUploader) {
        this.imageUploader = imageUploader;
    }

    @PostMapping("/profile")
    public ResponseEntity image(MultipartFile file, @AuthenticationPrincipal User principal) throws IOException {
        //imageUploader.uploadImage(file , principal.getUsername());
        return new ResponseEntity(new SingleResponse<>(imageUploader.uploadImage(file, principal.getUsername())), null, 200);
    }
}

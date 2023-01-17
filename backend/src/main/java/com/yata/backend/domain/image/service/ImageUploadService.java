package com.yata.backend.domain.image.service;

import com.yata.backend.domain.image.entity.ImageEntity;
import com.yata.backend.domain.image.repository.JpaImageRepository;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.uploadfile.service.Uploader;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
@Transactional
public class ImageUploadService implements ImageUploader {

    private final Uploader uploader;
    private final JpaImageRepository jpaImageRepository;
    private final MemberService memberService;

    public ImageUploadService(Uploader uploader, JpaImageRepository jpaImageRepository, MemberService memberService) {
        this.uploader = uploader;
        this.jpaImageRepository = jpaImageRepository;
        this.memberService = memberService;
    }

    @Override
    public String uploadImage(MultipartFile file, String email) throws IOException {

            String[] info  = uploadImage(file);
            ImageEntity imageEntity = ImageEntity.builder()
                    .bucket(info[1])
                    .url(info[0])
                    .build();
            Member member = memberService.findMember(email);
            member.setImgUrl(imageEntity);
            jpaImageRepository.save(imageEntity);
            return imageEntity.getUrl();

    }

    @Override
    public String[] uploadImage(MultipartFile file) throws IOException {
        System.out.println(file.getContentType());
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image")) {
            throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다.");
        }
        return uploader.upload(file);
    }

}

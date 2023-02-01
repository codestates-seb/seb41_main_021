package com.yata.backend.domain.uploadfile.config;

import com.yata.backend.domain.image.repository.JpaImageRepository;
import com.yata.backend.domain.image.service.ImageUploader;
import com.yata.backend.domain.image.service.ImageUploadService;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.uploadfile.service.Uploader;
import com.yata.backend.domain.uploadfile.service.S3Uploader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@Profile("prod")
public class ProdUploaderConfig {
    private final JpaImageRepository jpaImageRepository;
    private final MemberService memberService;
    public ProdUploaderConfig(JpaImageRepository jpaImageRepository, MemberService memberService) {
        this.jpaImageRepository = jpaImageRepository;
        this.memberService = memberService;
    }
    @Bean
    public ImageUploader imageUploader() {
        return new ImageUploadService(this.uploader(), jpaImageRepository, memberService);
    }

    @Bean
    public Uploader uploader() {
        return new S3Uploader(s3BucketConfig().amazonS3Client());
    }

    @Bean
    public S3BucketConfig s3BucketConfig() {
        return new S3BucketConfig();
    }
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(2000000000);
        return multipartResolver;
    }




}

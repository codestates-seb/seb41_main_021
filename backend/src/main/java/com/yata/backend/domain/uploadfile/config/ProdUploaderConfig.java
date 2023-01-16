package com.yata.backend.domain.uploadfile.config;

import com.yata.backend.domain.image.repository.JpaImageRepository;
import com.yata.backend.domain.image.service.ImageUploader;
import com.yata.backend.domain.image.service.S3ImageUploadService;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.uploadfile.service.Uploader;
import com.yata.backend.domain.uploadfile.service.S3Uploader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProdUploaderConfig {
    private final JpaImageRepository jpaImageRepository;
    private final MemberService memberService;

    public ProdUploaderConfig(JpaImageRepository jpaImageRepository, MemberService memberService) {
        this.jpaImageRepository = jpaImageRepository;
        this.memberService = memberService;
    }

    @Bean
    public ImageUploader imageUploader() {
        return new S3ImageUploadService(this.uploader(), jpaImageRepository, memberService);
    }
    @Bean
    public Uploader uploader() {
        return new S3Uploader(s3BucketConfig().amazonS3Client());
    }
    @Bean
    public S3BucketConfig s3BucketConfig() {
        return new S3BucketConfig();
    }

}

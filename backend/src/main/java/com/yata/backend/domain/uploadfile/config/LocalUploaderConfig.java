package com.yata.backend.domain.uploadfile.config;

import com.yata.backend.domain.image.repository.JpaImageRepository;
import com.yata.backend.domain.image.service.ImageUploadService;
import com.yata.backend.domain.image.service.ImageUploader;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.uploadfile.service.LocalUploader;
import com.yata.backend.domain.uploadfile.service.Uploader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class LocalUploaderConfig {
   private final JpaImageRepository jpaImageRepository;
   private final MemberService memberService;

   public LocalUploaderConfig(JpaImageRepository jpaImageRepository, MemberService memberService) {
      this.jpaImageRepository = jpaImageRepository;
      this.memberService = memberService;
   }

   @Bean
   public ImageUploader imageUploader() {
      return new ImageUploadService(this.uploader(), jpaImageRepository, memberService);
   }
   @Bean
   public Uploader uploader() {
      return new LocalUploader();
   }
}

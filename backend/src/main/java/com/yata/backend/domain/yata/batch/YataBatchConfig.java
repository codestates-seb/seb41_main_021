package com.yata.backend.domain.yata.batch;

import com.yata.backend.domain.yata.repository.yataRepo.JpaYataRepository;
import com.yata.backend.domain.yata.repository.yataRequestRepo.JpaYataRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@EnableBatchProcessing
public class YataBatchConfig {
    public final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final JpaYataRepository yataRepository;
    private final JpaYataRequestRepository yataRequestRepository;

    public YataBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, JpaYataRepository yataRepository, JpaYataRequestRepository yataRequestRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.yataRepository = yataRepository;
        this.yataRequestRepository = yataRequestRepository;
    }

    @Bean
    public Job yataJob() {
        return jobBuilderFactory.get("yataJob")
                .start(yataStep()).next(yataRequestStep())
                .build();
    }

    @Bean
    public Step yataStep() {
        return stepBuilderFactory.get("yataStep")
                .tasklet((contribution, chunkContext) -> {
                    yataRepository.updateYataOverDepartureTime();
                    return RepeatStatus.FINISHED;
                })
                .build();

    }

    public Step yataRequestStep() {
        return stepBuilderFactory.get("yataRequestStep")
                .tasklet((contribution, chunkContext) -> {
                    yataRequestRepository.updateExpiredYataRequest();
                    return RepeatStatus.FINISHED;
                })
                .build();

    }


}

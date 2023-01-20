package com.yata.backend.domain.yata.scheduler;

import com.yata.backend.domain.yata.batch.YataBatchConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class YataBatchScheduler {
    private final JobLauncher jobLauncher;
    private final YataBatchConfig yataBatchConfig;

    public YataBatchScheduler(JobLauncher jobLauncher, YataBatchConfig yataBatchConfig) {
        this.jobLauncher = jobLauncher;
        this.yataBatchConfig = yataBatchConfig;
    }
    // 10분 마다 실행
    //@Scheduled(cron = "0 0/10 * * * *")
    // 1분 마다 실행
    @Scheduled(fixedDelay = 1000 * 60 * 20) // 20분 마다 실행
    public void runExpiredYataBatch() {
        Map<String, JobParameter> jobParameter = new HashMap<>();
        jobParameter.put("yataJobExpire", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(jobParameter);
        try {
            jobLauncher.run(yataBatchConfig.yataJob(), jobParameters);
            log.info("YataBatchScheduler runExpiredYataBatch");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("YataBatchScheduler Error :{}", e.getMessage());
        }
    }
}

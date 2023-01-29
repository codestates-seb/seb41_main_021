package com.yata.backend.domain.notify.aop;

import com.yata.backend.domain.notify.dto.NotifyMessage;
import com.yata.backend.domain.notify.entity.Notify;
import com.yata.backend.domain.notify.service.NotifyService;
import com.yata.backend.domain.yata.entity.YataRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@EnableAsync
public class NotifyAspect {
    // yataRequestService  createRequest method pointcut
    private final NotifyService notifyService;

    public NotifyAspect(NotifyService notifyService) {
        this.notifyService = notifyService;
    }


    @Pointcut("@annotation(com.yata.backend.domain.notify.annotation.NeedNotify)")
    public void annotationPointcut() {
    }

    @Async
    @AfterReturning(pointcut = "annotationPointcut()", returning = "result")
    public void checkValue(JoinPoint joinPoint, Object result) throws Throwable {
        YataRequest yataResult = (YataRequest) result;
        notifyService.send(
                yataResult.getYata().getMember(),
                Notify.NotificationType.YATA,
                NotifyMessage.YATA_NEW_REQUEST.getMessage(),
                "/api/v1/yata/" + (yataResult.getYata().getYataId())
        );
        log.info("result = {}", result);
    }
}

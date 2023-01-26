package com.yata.backend.domain.yata.util;

import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import org.springframework.stereotype.Component;

@Component
public class TimeCheckUtils {

    public static void verifyTime(long departureTime, long compareTime) {
        if (departureTime <= compareTime) {
            throw new CustomLogicException(ExceptionCode.INVALID_TIME);
        }
    }
}

package com.yata.backend.domain.yata.util;

import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TimeCheckUtils {

    public static void verifyTime(long departureTime, long compareTime) {
        if (departureTime <= compareTime) {
            throw new CustomLogicException(ExceptionCode.INVALID_TIME);
        }
    }
    public static void isEqualsYearsMonthDay(Date departureTime, Date compareTime) {
        if (departureTime.getYear() != compareTime.getYear() ||
                departureTime.getMonth() != compareTime.getMonth() ||
                departureTime.getDay() != compareTime.getDay()) {
            throw new CustomLogicException(ExceptionCode.INVALID_TIME);
        }
    }
}

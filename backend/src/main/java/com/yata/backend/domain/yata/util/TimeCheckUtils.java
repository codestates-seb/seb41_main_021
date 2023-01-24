package com.yata.backend.domain.yata.util;

import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import org.springframework.stereotype.Component;

@Component
public class TimeCheckUtils {

    // 1. yata 게시물의 출발시간이 현재 시간과 비교하여 이미 지난 경우(마감인 경우) 익셉션 --> ( 신청 / 초대 / 승인 할 때 사용 )
    // 2. 게시물 작성 / 신청 폼에 적는 출발시간이 현재 시간보다 이전인 경우 익셉션 --> ( yata 게시물 생성 / 신청 할 때 사용 )
    public static void verifyTime(long departureTime, long compareTime) {
        if (departureTime <= compareTime) {
            throw new CustomLogicException(ExceptionCode.INVALID_TIME);
        }
    }
}

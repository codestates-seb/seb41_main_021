package com.yata.backend.domain.payHistory.factory;

import com.yata.backend.domain.Yata.factory.YataFactory;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.factory.MemberFactory;
import com.yata.backend.domain.payHistory.dto.PayHistoryDto;
import com.yata.backend.domain.payHistory.entity.PayHistory;
import com.yata.backend.domain.yata.entity.Yata;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.yata.backend.common.utils.RandomUtils.getRandomLong;

public class PayHistoryFactory {
    public static PayHistory createPayHistory() throws org.locationtech.jts.io.ParseException {
        Yata yata = YataFactory.createYata();
        Member member = MemberFactory.createMember(new BCryptPasswordEncoder());

        return PayHistory.builder()
                .payHistoryId(getRandomLong())
                .paidPrice(getRandomLong())
                .member(member)
                .type(PayHistory.Type.YATA)
                .gain(PayHistory.Gain.PAY)
                .build();
    }
    public static List<PayHistory> createPayHistoryList() throws java.text.ParseException, org.locationtech.jts.io.ParseException {
        List<PayHistory> payHistoryList = new ArrayList<>();
        for(int i=0; i<10; i++){
            payHistoryList.add(createPayHistory());
        }
        return payHistoryList;
    }

    public static PayHistoryDto.Response createPayHistoryResponseDto(PayHistory payHistory) {

        return PayHistoryDto.Response.builder()
                .payHistoryId(payHistory.getPayHistoryId())
                .email(payHistory.getMember().getEmail())
                .nickname(payHistory.getMember().getNickname())
                .paidPrice(payHistory.getPaidPrice())
                .point(payHistory.getMember().getPoint())
                .createdAt(LocalDateTime.now())
                .gain(payHistory.getGain())
                .type(payHistory.getType())
                .build();
    }

    public static List<PayHistoryDto.Response> createPayHistoryResponseDtoList(List<PayHistory> payHistoriesList){
        List<PayHistoryDto.Response> payHistoryResponseDtoList = new ArrayList<>();
        for(PayHistory payHistory : payHistoriesList){
            payHistoryResponseDtoList.add(createPayHistoryResponseDto(payHistory));
        }
        return payHistoryResponseDtoList;
    }
}

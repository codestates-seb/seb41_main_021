package com.yata.backend.domain.yataMember.factory;

import com.yata.backend.common.utils.RandomUtils;
import com.yata.backend.domain.Yata.factory.YataFactory;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.factory.MemberFactory;
import com.yata.backend.domain.yata.dto.YataMemberDto;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yataRequest.factory.YataRequestFactory;
import com.yata.backend.global.utils.GeometryUtils;
import org.locationtech.jts.io.ParseException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static com.yata.backend.common.utils.RandomUtils.getRandomLong;
import static com.yata.backend.common.utils.RandomUtils.getRandomWord;

public class YataMemberFactory {
    public static YataMember createYataMember() throws org.locationtech.jts.io.ParseException {
        Yata yata = YataFactory.createYata();
        Member member = MemberFactory.createMember(new BCryptPasswordEncoder());
//        YataRequest yataRequest = YataRequestFactory.createYataRequest();
//        yata.setYataRequests(List.of(yataRequest));

        return YataMember.builder()
                .YataMemberId(getRandomLong())
                .yata(yata)
                .member(member)
                .yataPaid(false)
                .goingStatus(YataMember.GoingStatus.STARTED_YET)
                .build();
    }

    public static List<YataMember> createYataMemberList() throws java.text.ParseException, org.locationtech.jts.io.ParseException {
        List<YataMember> yataMemberList = new ArrayList<>();
        for(int i=0; i<10; i++){
            yataMemberList.add(createYataMember());
        }
        return yataMemberList;
    }

    public static YataMemberDto.Response createYataMemberResponseDto(YataMember yataMember) {

        return YataMemberDto.Response.builder()
                .yataId(yataMember.getYata().getYataId())
                .yataMemberId(yataMember.getYataMemberId())
                .yataPaid(yataMember.isYataPaid())
                .goingStatus(yataMember.getGoingStatus())
                .build();
    }

    public static List<YataMemberDto.Response> createYataMemberResponseDtoList(List<YataMember> yataMembersList){
        List<YataMemberDto.Response> yataRequestResponseDtoList = new ArrayList<>();
        for(YataMember yataMember : yataMembersList){
            yataRequestResponseDtoList.add(createYataMemberResponseDto(yataMember));
        }
        return yataRequestResponseDtoList;
    }
}

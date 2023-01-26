package com.yata.backend.domain.yataMember.factory;

import com.yata.backend.domain.Yata.factory.YataFactory;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.factory.MemberFactory;
import com.yata.backend.domain.yata.dto.YataMemberDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static com.yata.backend.common.utils.RandomUtils.getRandomLong;

public class YataMemberFactory {
    public static YataMember createYataMember() throws org.locationtech.jts.io.ParseException {
        Yata yata = YataFactory.createYata();
        Member member = MemberFactory.createMember(new BCryptPasswordEncoder());

        return YataMember.builder()
                .yataMemberId(getRandomLong())
                .yata(yata)
                .member(member)
                .yataPaid(false)
                .boardingPersonCount(2)
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
                .email(yataMember.getMember().getEmail())
                .nickname(yataMember.getMember().getNickname())
                .yataPaid(yataMember.isYataPaid())
                .imgUrl(yataMember.getMember().getImgUrl() != null ? yataMember.getMember().getImgUrl().getUrl() : null)
                .boardingPersonCount(yataMember.getBoardingPersonCount())
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

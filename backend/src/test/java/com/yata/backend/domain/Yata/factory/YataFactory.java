package com.yata.backend.domain.Yata.factory;

import com.yata.backend.common.utils.RandomUtils;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.entity.YataStatus;
import com.yata.backend.global.utils.GeometryUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yata.backend.common.utils.RandomUtils.*;


public class YataFactory {

    public static YataDto.YataPost createYataPostDto() throws ParseException {

        return YataDto.YataPost.builder()
                .title("부산까지 같이가실 분~")
                .specifics("같이 노래들으면서 가요~")
                .departureTime(new Date())
                .timeOfArrival(new Date())
                .amount(2000L)
                .carModel("bmw")
                .maxPeople(3)
                .maxWaitingTime(20)
                .strPoint(new LocationDto.Post(5, 4, "인천"))
                .destination(new LocationDto.Post(3, 2, "부산"))
                .yataStatus(YataStatus.YATA_NEOTA)
                .build();
    }

    public static YataDto.Patch createYataPatchDto() throws ParseException {

        return YataDto.Patch.builder()
                .title("인천까지 같이가실 분~")
                .specifics("같이 춤추면서 가요~")
                .departureTime(new Date())
                .timeOfArrival(new Date())
                .amount(1500L)
                .carModel("porsche")
                .maxPeople(2)
                .strPoint(new LocationDto.Post(5, 4, "부산"))
                .destination(new LocationDto.Post(3, 2, "인천"))
                .maxWaitingTime(10)
                .build();
    }

    public static YataDto.Response createYataResponseDto(Yata yata) {
        YataDto.Response.ResponseBuilder response = YataDto.Response.builder();
        response.yataId(yata.getYataId())
                .title(yata.getTitle())
                .specifics(yata.getSpecifics())
                .timeOfArrival(yata.getTimeOfArrival())
                .departureTime(yata.getDepartureTime())
                .createdAt(yata.getCreatedAt())
                .modifiedAt(yata.getModifiedAt())
                .amount(yata.getAmount())
                .carModel(yata.getCarModel())
                .maxPeople(yata.getMaxPeople())
                .nickName(yata.getMember().getNickname())
                .maxWaitingTime(yata.getMaxWaitingTime())
                .yataStatus(yata.getYataStatus())
                .postStatus(yata.getPostStatus())
                .fuelTank(70.0)
                .yataMembers(null)
                .reservedMemberNum(0)
                .strPoint(new LocationDto.Response(
                        yata.getStrPoint().getLocation().getX(),
                        yata.getStrPoint().getLocation().getY(),
                        yata.getStrPoint().getAddress()))
                .destination(new LocationDto.Response(
                        yata.getDestination().getLocation().getX(),
                        yata.getDestination().getLocation().getY(),
                        yata.getDestination().getAddress()))
                .email(RandomUtils.getRandomWord() + "@gmail.com");

        return response.build();

    }

    public static Yata createYata() throws org.locationtech.jts.io.ParseException {
        List<YataMember> yatamembers = new ArrayList<>();
        yatamembers.add(new YataMember(1L, true,true,true,2,YataMember.GoingStatus.STARTED_YET, new Yata(1L), null));
        Member member = new Member();
        member.setNickname("채은");
        Yata yata = Yata.builder()
                .yataId(getRandomLong())
                .title(getRandomWord())
                .specifics(getRandomWord())
                .departureTime(getRandomDate())
                .timeOfArrival(getRandomDate())
                .amount(getRandomLong())
                .member(member)
                .yataMembers(yatamembers)
                .carModel(getRandomWord(20))
                .maxPeople(3)
                .maxWaitingTime(20)
                .yataStatus(YataStatus.YATA_NEOTA)
                .postStatus(Yata.PostStatus.POST_OPEN)
                .strPoint(new Location(RandomUtils.getRandomLong(), GeometryUtils.getEmptyPoint(), getRandomWord(), null))
                .destination(new Location(RandomUtils.getRandomLong(), GeometryUtils.getEmptyPoint(), getRandomWord(), null))
                .build();
        yata.setCreatedAt(LocalDateTime.of(LocalDate.now(), LocalDateTime.now().toLocalTime()));
        yata.setModifiedAt(LocalDateTime.of(LocalDate.now(), LocalDateTime.now().toLocalTime()));

        return yata;
    }

    public static List<Yata> createYataList() throws ParseException, org.locationtech.jts.io.ParseException {
        List<Yata> yataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            yataList.add(createYata());
        }
        return yataList;
    }

    public static List<YataDto.Response> createYataResponseDtoList(List<Yata> yataList) {
        List<YataDto.Response> yataResponseDtoList = new ArrayList<>();
        for (Yata yata : yataList) {
            yataResponseDtoList.add(createYataResponseDto(yata));
        }
        return yataResponseDtoList;
    }

    public static Yata createYataInMember(Member member) throws org.locationtech.jts.io.ParseException {
        List<YataMember> yatamembers = new ArrayList<>();
        Yata yata = createYata();
        yata.setYataMembers(yatamembers);
        yata.setMember(member);
        return yata;
    }
    public static YataDto.AcceptedResponse createYataAcceptedResponseDto(Yata yata){
        YataDto.Response response = new YataDto.Response();
        response.setYataId(yata.getYataId());
        response.setTitle(yata.getTitle());
        response.setSpecifics(yata.getSpecifics());
        response.setTimeOfArrival(yata.getTimeOfArrival());
        response.setDepartureTime(yata.getDepartureTime());
        response.setCreatedAt(yata.getCreatedAt());
        response.setModifiedAt(yata.getModifiedAt());
        response.setAmount(yata.getAmount());
        response.setCarModel(yata.getCarModel());
        response.setMaxPeople(yata.getMaxPeople());
        response.setNickName(yata.getMember().getNickname());
        response.setMaxWaitingTime(yata.getMaxWaitingTime());
        response.setYataStatus(yata.getYataStatus());
        response.setPostStatus(yata.getPostStatus());
        response.setFuelTank(70.0);
        response.setYataMembers(null);
        response.setReservedMemberNum(0);
        response.setStrPoint(new LocationDto.Response(
                        yata.getStrPoint().getLocation().getX(),
                        yata.getStrPoint().getLocation().getY(),
                        yata.getStrPoint().getAddress()));
        response.setDestination(new LocationDto.Response(
                        yata.getDestination().getLocation().getX(),
                        yata.getDestination().getLocation().getY(),
                        yata.getDestination().getAddress()));
        response.setEmail(RandomUtils.getRandomWord() + "@gmail.com");

        return YataDto.AcceptedResponse.builder()
                .yataResponse(response)
                .yataPaid(true)
                .reviewReceived(true)
                .reviewWritten(true)
                .yataMemberId(1L)
                .goingStatus(YataMember.GoingStatus.STARTED_YET).build();
    }

    public static List<YataDto.AcceptedResponse> createYataAcceptedResponseDtoList(List<Yata> yataList){
        List<YataDto.AcceptedResponse> yataAcceptedResponseDtoList = new ArrayList<>();
        for (Yata yata : yataList) {
            yataAcceptedResponseDtoList.add(createYataAcceptedResponseDto(yata));
        }
        return yataAcceptedResponseDtoList;
    }
}

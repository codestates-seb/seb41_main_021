package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataStatus;
import com.yata.backend.domain.yata.repository.yataRepo.JpaYataRepository;
import com.yata.backend.domain.yata.repository.yataRequestRepo.JpaYataRequestRepository;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import com.yata.backend.global.utils.CustomBeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class YataServiceImpl implements YataService {

    private final MemberService memberService;
    private final JpaYataRepository jpaYataRepository;
    private final CustomBeanUtils<Yata> beanUtils;


    public YataServiceImpl(JpaYataRepository jpaYataRepository, CustomBeanUtils<Yata> beanUtils, MemberService memberService) {
        this.jpaYataRepository = jpaYataRepository;
        this.beanUtils = beanUtils;
        this.memberService = memberService;
    }

    @Override
    public Yata createYata(Yata yata, String userName) {

        Member member = memberService.findMember(userName);

        if (yata.getYataStatus().equals(YataStatus.YATA_NEOTA)) memberService.checkDriver(member);
        compareTime(yata);
        yata.setMember(member);

        return jpaYataRepository.save(yata);
    }


    @Override
    public Yata updateYata(long yataId, Yata yata, String userName) {

        //존재하는 멤버인지 확인
        Member member = memberService.findMember(userName);
        //존재하는 게시물인지 확인
        Yata findYata = findYata(yataId);
        //게시글 작성자와 같은 멤버인지 확인
        equalMember(member.getEmail(), findYata.getMember().getEmail());

        //예완 상태 아닌 게시물인지 확인
        //예안 게시물이면 -> 예외
        modifiableYata(yataId);

        Yata updatingYata = beanUtils.copyNonNullProperties(yata, findYata);

        return jpaYataRepository.save(updatingYata);
    }

    @Override
    public void deleteYata(long yataId, String username) {

        Member member = memberService.findMember(username);
        Yata findYata = verifyYata(yataId);
        //게시글 작성자와 같은 멤버인지 확인
        equalMember(member.getEmail(), findYata.getMember().getEmail());
        modifiableYata(yataId);
        // TODO 시간 validate 추가 해야함

        jpaYataRepository.delete(findYata);
    }
//public Slice<YataRequest> findRequests(boolean acceptable, String userEmail, Long yataId, Pageable pageable) {

    //     return jpaYataRequestRepository.findAllByYata(yata, pageable);
//    }
    @Override
    public Slice<Yata> findAllYata(String yataStatus, Pageable pageable) {
        YataStatus nowStatus;
        switch (yataStatus) {
            case "neota" -> nowStatus = YataStatus.YATA_NEOTA;
            case "nata" -> nowStatus = YataStatus.YATA_NATA;
            default -> throw new CustomLogicException(ExceptionCode.YATA_STATUS_NONE);
        }
        return jpaYataRepository.findAllByYataStatusIs(nowStatus, PageRequest.of(0, 20, Sort.by("yataId").descending()));
    }

    @Override
    public Yata findYata(long yataId) {
        return verifyYata(yataId);
    }

    /*검증로직*/
    private Yata verifyYata(long yataId) {
        Optional<Yata> optionalYata = jpaYataRepository.findById(yataId);
        Yata findYata = optionalYata.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.YATA_NONE));
        return findYata;
    }

    private void modifiableYata(long yataId) {
        Yata findYata = verifyYata(yataId);

        if (!findYata.getYataMembers().isEmpty()) {
            throw new CustomLogicException(ExceptionCode.YATA_IS_NOT_MODIFIABLE_STATUS);
        }
    }

    public void equalMember(String email, String postEmail) {
        if (!email.equals(postEmail)) {
            throw new CustomLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }

    private void compareTime(Yata yata) {
        //출발시간이 도착시간보다 전이면 에러
        if (yata.getTimeOfArrival().before(yata.getDepartureTime()))
            throw new CustomLogicException(ExceptionCode.INVALID_TIME_OF_ARRIVAL);

    }
}

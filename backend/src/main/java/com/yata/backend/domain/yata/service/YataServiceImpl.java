package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataStatus;
import com.yata.backend.domain.yata.repository.yataRepo.JpaYataRepository;
import com.yata.backend.domain.yata.util.TimeCheckUtils;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import com.yata.backend.global.utils.CustomBeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
        //도착시간이 출발시간보다 빠르면 에러
        TimeCheckUtils.verifyTime( yata.getArrivalTimeByLong(),yata.getDepartureTimeByLong());
        //출발시간이 현재시간보다 빠르면 에러
        TimeCheckUtils.verifyTime(yata.getDepartureTimeByLong(),System.currentTimeMillis());
        yata.setMember(member);
        return jpaYataRepository.save(yata);
    }


    @Override
    public Yata updateYata(long yataId, Yata yata, String userName) {

        Member member = memberService.findMember(userName);

        Yata findYata = findYata(yataId);

        equalMember(member.getEmail(), findYata.getMember().getEmail());

        modifiableYata(yataId);

        Yata updatingYata = beanUtils.copyNonNullProperties(yata, findYata);

        return jpaYataRepository.save(updatingYata);
    }

    @Override
    public void deleteYata(long yataId, String username) {

        Member member = memberService.findMember(username);
        Yata findYata = verifyYata(yataId);
        equalMember(member.getEmail(), findYata.getMember().getEmail());
        modifiableYata(yataId);
        jpaYataRepository.delete(findYata);
    }

    @Override
    public Slice<Yata> findAllYata(String yataStatus, Pageable pageable) {
        YataStatus nowStatus;
        switch (yataStatus) {
            case "neota" -> nowStatus = YataStatus.YATA_NEOTA;
            case "nata" -> nowStatus = YataStatus.YATA_NATA;
            default -> throw new CustomLogicException(ExceptionCode.YATA_STATUS_NONE);
        }
        return jpaYataRepository.findAllByYataStatusIs(nowStatus, pageable);
    }

    @Override
    public Slice<Yata> findMyRequestedYatas(String userName, Pageable pageable){

        Member member = memberService.findMember(userName);
        return jpaYataRepository.findAllByMemberAndYata_YataMembersIsNotNull(pageable, member);
    }
    @Override
    public Slice<Yata> findMyAcceptedYata(String userName,Pageable pageable){

        Member member = memberService.findMember(userName);
        return jpaYataRepository.findAllByYata_YataMember_Member(pageable, member);
    }

    @Override
    public Slice<Yata> findMyYatas(String userName, Pageable pageable , String yataStatus , Boolean isExpired) {

        memberService.findMember(userName);
        return jpaYataRepository.findAllByMember_Email(userName, pageable , yataStatus , isExpired);
    }

    @Override
    public Yata findYata(long yataId) {
        return verifyYata(yataId);
    }

    /*검증로직*/
    private Yata verifyYata(long yataId) {
        Optional<Yata> optionalYata = jpaYataRepository.findById(yataId);
        return optionalYata.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.YATA_NONE));
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

    @Override
    public List<Yata> findMyYatasNotClosed(String username) {
        return jpaYataRepository.findAllByMember_EmailAndYataStatusIsNot(username, Yata.PostStatus.POST_OPEN);
    }
}

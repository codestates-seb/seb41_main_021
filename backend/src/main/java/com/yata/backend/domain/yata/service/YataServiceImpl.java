package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataStatus;
import com.yata.backend.domain.yata.repository.yataRepo.JpaYataRepository;
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
public class YataServiceImpl implements YataService{

    private final MemberService memberService;

    private final JpaYataRepository jpayataRepository;
    private final CustomBeanUtils<Yata> beanUtils;



    public YataServiceImpl(JpaYataRepository jpayataRepository,CustomBeanUtils<Yata> beanUtils,MemberService memberService){
        this.jpayataRepository = jpayataRepository;
        this.beanUtils = beanUtils;
        this.memberService = memberService;
    }

    @Override
    public Yata createYata(Yata yata, String userName) {

        Member member = memberService.findMember(userName);

        if(yata.getYataStatus().equals(YataStatus.YATA_NEOTA)) memberService.checkDriver(member);

        yata.setMember(member);

        return jpayataRepository.save(yata);
    }


    @Override
    public Yata updateYata(long yataId, Yata yata, String userName) {

        //존재하는 멤버인지 확인
        Member member = memberService.findMember(userName);
        //존재하는 게시물인지 확인
        Yata findYata = verifyYata(yataId);
        //게시글 작성자와 같은 멤버인지 확인
        equalMember(member, findYata.getMember());

        //예완 상태 아닌 게시물인지 확인
        //예안 게시물이면 -> 예외
        modifiableYata(yataId);

        Yata updatingYata = beanUtils.copyNonNullProperties(yata, findYata);

        return jpayataRepository.save(updatingYata);
    }

    @Override
    public void deleteYata(long yataId, String username) {

        Member member = memberService.findMember(username);
        Yata findYata = verifyYata(yataId);
        //게시글 작성자와 같은 멤버인지 확인
        equalMember(member, findYata.getMember());
        switch (findYata.getPostStatus()) {
            case POST_MOVING, POST_CLOSED, POST_RESERVED, POST_WARNING ->
                    throw new CustomLogicException(ExceptionCode.CANNOT_DELETE);
            default -> jpayataRepository.delete(findYata);
        }
    }
//public Slice<YataRequest> findRequests(boolean acceptable, String userEmail, Long yataId, Pageable pageable) {

//     return jpaYataRequestRepository.findAllByYata(yata, pageable);
//    }
    @Override
    public Slice<Yata> findAllYata(String yataStatus,Pageable pageable) {
        YataStatus nowStatus;
        switch(yataStatus){
            case "neota" -> nowStatus = YataStatus.YATA_NEOTA;
            case "nata" -> nowStatus = YataStatus.YATA_NATA;
            default -> throw new CustomLogicException(ExceptionCode.YATA_STATUS_NONE);
        }
        return jpayataRepository.findAllByYataStatusIs(nowStatus, PageRequest.of(0,20, Sort.by("yataId").descending()));
    }

    @Override
    public Yata findYata(long yataId) {
        Yata findYata = verifyYata(yataId);
        return findYata;
    }

    /*검증로직*/
    public Yata verifyYata(long yataId) {
        Optional<Yata> optionalYata = jpayataRepository.findById(yataId);
        Yata findYata = optionalYata.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.YATA_NONE));
        return findYata;
    }

    private void modifiableYata(long yataId) {
        Yata findYata = verifyYata(yataId);
        int statusNumber = findYata.getPostStatus().getStatusNumber();
        //예약 전 상태가 아니면 게시물 변경 못하게
        if (statusNumber > 1) {
            throw new CustomLogicException(ExceptionCode.YATA_IS_NOT_MODIFIABLE_STATUS);
        }
    }

    private void equalMember(Member member, Member postMember) {
        if (!member.getEmail().equals(postMember.getEmail())) {
            throw new CustomLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }
}

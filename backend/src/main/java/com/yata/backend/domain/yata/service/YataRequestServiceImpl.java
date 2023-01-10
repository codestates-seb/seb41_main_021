package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.repository.JpaMemberRepository;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.repository.yataRepo.JpaYataRepository;
import com.yata.backend.domain.yata.repository.yataRequestRepo.JpaYataRequestRepository;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class YataRequestServiceImpl implements YataRequestService {
    private final JpaYataRequestRepository jpaYataRequestRepository;
    private final MemberService memberService;
    private final JpaMemberRepository jpaMemberRepository;
    private final JpaYataRepository jpaYataRepository;

    public YataRequestServiceImpl(JpaYataRequestRepository jpaYataRequestRepository, MemberService memberService, JpaMemberRepository jpaMemberRepository, JpaYataRepository jpaYataRepository) {
        this.jpaYataRequestRepository = jpaYataRequestRepository;
        this.memberService = memberService;
        this.jpaMemberRepository = jpaMemberRepository;
        this.jpaYataRepository = jpaYataRepository;
    }

    // TODO Yata 신청 ( yata 로직과 합쳐지면 throws Exception 빼고 로직도 거기서 가져오기 )
    @Override
    public YataRequest createRequest(YataRequest yataRequest, String userName, long yataId) throws Exception {
        Member member = memberService.findMember(userName); // 해당 멤버가 있는지 확인하고
        verifyRequest(yataRequest); // 신청을 이미 했었는지 확인하고
        Yata yata = findYata(yataId);
        // TODO 체크리스트 추가
        YataRequest request = YataRequest.create(yataRequest, member, yata);
        return jpaYataRequestRepository.save(request);
        // TODO 신청하면 신청 list 에 추가되고 / 운전자가 승인을 하게 되면 신청 목록에서 delete 되고 / 탑승자 list 에 추가됨
        //  delete 되는 건 일단 놔둬도 될 듯 ( 신청 내역에서 볼 수 있도록 )
    }

    // TODO Yata 초대 ( yata 로직과 합쳐지면 throws Exception 빼고 로직도 거기서 가져오기 )
    @Override
    public YataRequest createInvitation(YataRequest yataRequest, String userName, long yataId) throws Exception {
        Member member = memberService.findMember(userName); // 해당 멤버가 있는지 확인하고
        verifyInvitation(yataRequest); // 초대를 이미 했었는지 확인하고
        Yata yata = findYata(yataId);
        // TODO 체크리스트 추가
        YataRequest request = YataRequest.create(yataRequest, member, yata);
        // TODO 초대 후, 운전자의 탑승자 list 에 추가
        return jpaYataRequestRepository.save(request);
    }

    // TODO Yata 신청 목록 조회
    @Override
    public Page<YataRequest> findRequests(int page, int size) {
        return null;
    }

    // TODO Yata 신청 취소 / 초대 취소
    // 해당 id 로 한 신청/초대가 있는지 검증 + 승인이 된 신청/초대 인지 검증
    @Override
    public void deleteRequest(long yataRequestId) {
    }

    // TODO 승인하는 로직,,? 만약 status

    // 이미 신청한 게시물인지 검증
    @Override
    public void verifyRequest(YataRequest yataRequest) {
        Optional<YataRequest> optionalYataRequest = jpaYataRequestRepository.findByMember_EmailAndYata_YataId(yataRequest.getMember().getEmail(), yataRequest.getYata().getYataId());
        optionalYataRequest.ifPresent(
                yr -> {throw new CustomLogicException(ExceptionCode.ALREADY_APPLIED);}
        );
    }

    // 이미 초대한 게시물인지 검증
    @Override
    public void verifyInvitation(YataRequest yataRequest) {
        Optional<YataRequest> optionalYataRequest = jpaYataRequestRepository.findByMember_EmailAndYata_YataId(yataRequest.getMember().getEmail(), yataRequest.getYata().getYataId());
        optionalYataRequest.ifPresent(
                yr -> {throw new CustomLogicException(ExceptionCode.ALREADY_INVITED);}
        );
    }

    // TODO yata 의 검증 로직 일단 대충 만들어 놓음 ( yata 랑 합쳐지면 거기 꺼 가져오기 )
    public Yata findYata(Long yataId) throws Exception {
        return jpaYataRepository.findById(yataId).orElseThrow(Exception::new);
    }
}

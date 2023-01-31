package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class YataInviteServiceImpl implements YataInviteService {
    private final MemberService memberService;
    private final YataRequestService yataRequestService;
    private final YataMemberService yataMemberService;

    public YataInviteServiceImpl(MemberService memberService, YataRequestService yataRequestService, YataMemberService yataMemberService) {
        this.memberService = memberService;
        this.yataRequestService = yataRequestService;
        this.yataMemberService = yataMemberService;
    }

    @Override
    public void acceptInvitation(String username, Long yataRequestId) {
        Member member = memberService.findMember(username);
        YataRequest yataRequest = yataRequestService.findRequest(yataRequestId);
        validateInviteRequest(member, yataRequest);
        yataMemberService.validateRequest(yataRequestId, yataRequest.getYata().getYataId(), yataRequest.getYata(), yataRequest);
        yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.ACCEPTED);
        yataMemberService.saveYataMember(yataRequest);

    }

    @Override
    public void rejectInvitation(String username, Long yataRequestId) {
        Member member = memberService.findMember(username);
        YataRequest yataRequest = yataRequestService.findRequest(yataRequestId);
        validateInviteRequest(member, yataRequest);
        yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.REJECTED);
    }

    private static void validateInviteRequest(Member member, YataRequest yataRequest) {
        if (!yataRequest.getMember().equals(member)) {
            throw new CustomLogicException(ExceptionCode.UNAUTHORIZED);
        }
        if (yataRequest.getRequestStatus().equals(YataRequest.RequestStatus.APPLY)) {
            throw new CustomLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }
}

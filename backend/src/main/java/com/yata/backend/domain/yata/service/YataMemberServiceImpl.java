package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.YataMember;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class YataMemberServiceImpl implements YataMemberService {


    // TODO yata 승인 / 거절
//    @Override
//    public YataMember acceptOrReject(YataMember yataMember, String userName, Long yataRequestId) {
//        // 일단 해당 yataRequest 가 있는지 확인하고
//
//        // switch 로
//        // approval 상태 저장
//
//        return null;
//    }
//
//    // TODO 승인된 yata 전체 조회
//    @Override
//    public Slice<YataMember> findAcceptedRequests() {
//        return null;
//    }
//
//    // TODO 승인된 yata 삭제 ( 운전자만 )
//    @Override
//    public void deleteRequest() {
//        // 승인 안받았으면 --> 삭제 가능
//        // 승인 받았으면 -->
//        // 해당 yata 게시물의 출발시간을 확인해서 현재 시각(new Date())으로 부터 2일(48시간)보다 많이 남았다면 --> 삭제
//        // 2일(48시간) 이하로 남았다면 --> 운전자만 삭제 가능
//    }
}

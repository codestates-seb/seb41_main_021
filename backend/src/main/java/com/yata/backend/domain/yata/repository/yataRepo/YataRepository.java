package com.yata.backend.domain.yata.repository.yataRepo;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;


public interface YataRepository {
    Slice<Yata> findYataByStartAndEndLocation(Location startLocation, Location endLocation, double distance,YataStatus yataStatus , Pageable pageable);
    void updateYataOverDepartureTime();
    Slice<Yata> findAllByYataStatusIs(YataStatus yataStatus, Pageable pageable);
    Slice<Yata> findAllByMemberAndYata_YataMembersIsNotNull(Pageable pageable, Member member);
    Slice<Yata> findAllByMember_Email(String userName, Pageable pageable);
    Slice<Yata> findAllByYata_YataMember_Member(Pageable pageable, Member member);
    List<Yata> findAllByMember_EmailAndYataStatusIsNot(String username, Yata.PostStatus postOpen);


}

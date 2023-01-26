package com.yata.backend.domain.yata.repository.yataRepo;

import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface YataRepository {
    Slice<Yata> findYataByStartAndEndLocation(Location startLocation, Location endLocation, double distance , Pageable pageable);
    void updateYataOverDepartureTime();
    Slice<Yata> findAllByYataStatusIs(YataStatus yataStatus, Pageable pageable);
}

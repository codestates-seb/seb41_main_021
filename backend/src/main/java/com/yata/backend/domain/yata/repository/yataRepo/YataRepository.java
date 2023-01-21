package com.yata.backend.domain.yata.repository.yataRepo;

import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface YataRepository {
    List<Yata> findYataByStartAndEndLocation(Location startLocation, Location endLocation,double distance , Pageable pageable);
    void updateYataOverDepartureTime();
}

package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface YataSearchService {
    Slice<Yata> findYataByLocation(Location startLocation, Location endLocation , double distance, YataStatus yataStatus, Pageable pageable);
}

package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface YataSearchService {
    List<Yata> findYataByLocation(Location startLocation, Location endLocation , Pageable pageable);
}

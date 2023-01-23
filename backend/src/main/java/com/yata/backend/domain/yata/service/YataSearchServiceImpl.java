package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.repository.yataRepo.JpaYataRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YataSearchServiceImpl implements YataSearchService{


   private final JpaYataRepository yataRepository;

   public YataSearchServiceImpl(JpaYataRepository yataRepository) {
      this.yataRepository = yataRepository;
   }


   public Slice<Yata> findYataByLocation(Location startLocation, Location endLocation, double distance , Pageable pageable) {
      return yataRepository.findYataByStartAndEndLocation(startLocation, endLocation, distance ,pageable);
   }
}

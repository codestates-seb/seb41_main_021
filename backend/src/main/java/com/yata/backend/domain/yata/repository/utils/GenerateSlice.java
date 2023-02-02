package com.yata.backend.domain.yata.repository.utils;

import com.querydsl.jpa.impl.JPAQuery;
import com.yata.backend.domain.yata.entity.Yata;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public class GenerateSlice {
   public static Slice<?> generateYataSlice(Pageable pageable, JPAQuery<?> yataJPAQuery) {
      List<?> list = yataJPAQuery.fetch();
      boolean hasNext = false;
      if (list.size() > pageable.getPageSize()) {
         list.remove(pageable.getPageSize());
         hasNext = true;
      }
      return new SliceImpl<>(list, pageable, hasNext);
   }
}

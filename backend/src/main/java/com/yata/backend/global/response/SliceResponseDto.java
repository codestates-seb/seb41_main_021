package com.yata.backend.global.response;

import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SliceResponseDto<T>{
    List<T> data;
    SliceInfo sliceInfo;
}

package com.yata.backend.global.response;

import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SliceResponseDto<T>{
    Slice<T> data;
    SliceInfo sliceInfo;
}

package com.yata.backend.domain.yata.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class LocationDto {

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class Post {
        @NotNull
        private double longitude;

        @NotNull
        private double latitude;

        @NotBlank
        private String address;

        public static Post of(double longitude, double latitude, String address) {
            return Post.builder()
                    .longitude(longitude)
                    .latitude(latitude)
                    .address(address)
                    .build();
        }
    }

    @Getter
    @ToString
    @Builder
    public static class Patch {

        private double longitude;

        private double latitude;

        private String address;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private double longitude;

        private double latitude;

        private String address;

    }
}



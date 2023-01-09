package com.yata.backend.domain.yata.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

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
        @NotBlank
        private double longitude;

        @NotBlank
        private double latitude;

        @NotBlank
        private String address;
    }

    public static class Response {
        private double longitude;

        private double latitude;

        private String address;

    }
    }



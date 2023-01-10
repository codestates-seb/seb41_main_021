package com.yata.backend.global.exception;

import lombok.Getter;
@Getter
public enum ExceptionCode {

   TITLE_NONE(400, "TITLE_NONE"),
   MEMBER_NONE(404, "MEMBER_NONE"),

   MEMBER_DUPLICATE(409, "MEMBER_DUPLICATE"),

   TOKEN_INVALID(401 , "TOKEN_INVALID"),
   EMAIL_NONE(400, "EMAIL_NEEDED"),
   TOKEN_NOT_EXPIRED(400, "TOKEN_NOT_EXPIRED"),
   REFRESH_TOKEN_NOT_FOUND(400, "REFRESH_TOKEN_NOT_FOUND"),
   REFRESH_TOKEN_INVALID(400, "REFRESH_TOKEN_INVALID"),
   REFRESH_TOKEN_NOT_MATCH(400, "REFRESH_TOKEN_NOT_MATCH"),

   YATA_NONE(404,"YATA_NONE"),

   YATA_IS_NOT_MODIFIABLE_STATUS(400,"YATA_IS_NOT_MODIFIABLE_STATUS");

   @Getter
   private final int code;

   @Getter
   private final String message;

   ExceptionCode(int code, String message) {
      this.code = code;
      this.message = message;
   }
}

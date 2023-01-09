package com.yata.backend.global.exception;

import lombok.Getter;
@Getter
public enum ExceptionCode {

   TITLE_NONE(400, "TITLE_NONE"),
   MEMBER_NONE(404, "MEMBER_NONE"),

   MEMBER_DUPLICATE(409, "MEMBER_DUPLICATE"),

   TOKEN_INVALID(401 , "TOKEN_INVALID"),
   EMAIL_NONE(400, "EMAIL_NEEDED"),

   YATA_NONE(404, "YATA_NONE"),

   YATA_IS_NOT_MODIFIABLE_STATUS(403,"YATA_IS_NOT_MODIFIABLE_STATUS");

   @Getter
   private final int code;

   @Getter
   private final String message;

   ExceptionCode(int code, String message) {
      this.code = code;
      this.message = message;
   }
}

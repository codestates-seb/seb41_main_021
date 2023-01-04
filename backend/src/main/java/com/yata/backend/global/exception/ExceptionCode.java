package com.yata.backend.global.exception;

import lombok.Getter;
@Getter
public enum ExceptionCode {

   TITLE_NONE(400, "TITLE_NONE"),
   MEMBER_NONE(404, "MEMBER_NONE"),

   MEMBER_DUPLICATE(409, "MEMBER_DUPLICATE"),

   QUESTION_NOT_FOUND(404, "Question not found"),
   QUESTION_WRITER_NOT_MATCH(409,"QUESTION WRITER NOT MATCH"),
   ANSWER_WRITER_NOT_MATCH(409,"ANSWER WRITER NOT MATCH"),

   ANSWER_NOT_FOUND(404, "Answer not found"),

   TOKEN_INVALID(401 , "TOKEN_INVALID");

   @Getter
   private final int code;

   @Getter
   private final String message;

   ExceptionCode(int code, String message) {
      this.code = code;
      this.message = message;
   }
}

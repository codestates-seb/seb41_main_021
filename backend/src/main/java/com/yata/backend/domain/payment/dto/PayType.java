package com.yata.backend.domain.payment.dto;

public enum PayType {
     CARD("카드"),
     CASH("현금"),
     POINT("포인트");

     private String description;

     PayType(String description) {
          this.description = description;
     }

     public String getDescription() {
          return description;
     }
}

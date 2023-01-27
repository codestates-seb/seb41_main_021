package com.yata.backend.global.helper.email.template;


public class EmailAuthTemplate {


    public static String emailAuth( String authCode) {
        return createTemplate(authCode);
    }

    private static String createTemplate(String... message) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\r");
        sb.append("<html>\r");
        sb.append("<head>\r");
        sb.append("<meta charset=\"UTF-8\">\r");
        sb.append("<title>YATA</title>\r");
        sb.append("</head>\r");
        sb.append("<body>\r");
        sb.append("<div style=\"width: 100%; height: 100%; background-color: #f5f5f5;\">\r");
        sb.append("<div style=\"margin: 0 auto; width: 600px; height: 100%; background-color: #ffffff;\">\r");
        sb.append("<div style=\"width: 100%; height: 100px; background-color: #ffffff;\">\r");
        //sb.append("<a href=\"http://localhost:8080/\">\r");
        sb.append("<img src=\"https://cdn.discordapp.com/attachments/1059646488466174009/1060029438387036160/240a87301be6586a.png\" style=\"width: 150px; height: 100px; margin-left: 20px;\">\r");
        sb.append("</a>\r");
        sb.append("</div>\r");
        sb.append("<div style=\"width: 100%; height: 400px; background-color: #ffffff;\">\r");
        sb.append("<div style=\"width: 100%; height: 100px; background-color: #ffffff;\">\r");
        sb.append("<h3 style=\"font-size: 25px; font-weight: 500; margin-left: 20px;\">Welcome to YATA!!</h3>\r");
        sb.append("</div>\r");
        sb.append("<div style=\"width: 100%; height: 300px; background-color: #ffffff;\">\r");
        sb.append("<p style=\"font-size: 15px; font-weight: 300; margin-left: 20px;\">YATA를 이용해주셔서 감사합니다.</p>\r");
        sb.append("<p style=\"font-size: 15px; font-weight: 300; margin-left: 20px;\">아래 인증번호를 입력 후 30분 이내에 가입을 완료해주세요.</p>\r");
        sb.append("<p style=\"font-size: 15px; font-weight: 300; margin-left: 20px;\">인증번호 : " + message[0] + "</p>\r");
        sb.append("</div>\r");
        sb.append("</div>\r");
        sb.append("<div style=\"width: 100%; height: 100px; background-color: #ffffff;\">\r");
        sb.append("<p style=\"font-size: 15px; font-weight: 300; margin-left: 20px;\">YATA</p>\r");
        sb.append("</div>\r");
        sb.append("</div>\r");
        sb.append("</div>\r");
        sb.append("</body>\r");
        sb.append("</html>\r");
        return sb.toString();
    }
}

package com.yata.backend.common.utils;

import com.yata.backend.domain.review.entity.Checklist;

import java.util.List;
import java.util.Random;

public class RandomUtils {
   public static final Random random = new Random();
   public static String getRandomWord() {
       StringBuilder sb = new StringBuilder();
       int length = random.nextInt(20) + 10;
       for (int i = 0; i < length; i++) {
           int random = (int) (Math.random() * 26) + 97;
           sb.append((char) random);
       }
       return sb.toString();
   }
   public static Long getRandomLong() {
       return (long) (Math.random() * 50000) + 10000;
   }

    public static boolean getRandomBoolean() {
        return random.nextBoolean();
    }


}

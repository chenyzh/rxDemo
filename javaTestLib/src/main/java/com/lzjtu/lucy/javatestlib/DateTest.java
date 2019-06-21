package com.lzjtu.lucy.javatestlib;

import java.util.Date;

public class DateTest {

  static long startTime = 1561120199000l;
  static long endedTime = 1561123799000l;

  public static void main(String[] args) {
    System.out.println(String.format("startData:%s", new Date(startTime).toString()));
    System.out.println(String.format("endData:%s", new Date(endedTime).toString()));
    System.out.println(getIntervalDays(/*System.currentTimeMillis()*/startTime, endedTime));

    long i = (60_000 * 59 / 60_000) % 60;
    System.out.println("i : " + i);
  }

  static String getIntervalDays(long now, long endTime) {
    long millis = Math.max(0, endTime - now);
    long sec = millis / (1000) % 60;
    long min = millis / (1000 * 60) % 60;
    long hours = millis / (3600_000);
    long days = millis / (24 * 3600_000);
    return days + "days\t" + hours + ":" + min+":"+sec;
  }

}

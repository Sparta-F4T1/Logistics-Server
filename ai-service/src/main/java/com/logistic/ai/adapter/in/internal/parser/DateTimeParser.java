package com.logistic.ai.adapter.in.internal.parser;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeParser {
  private static final Pattern DATETIME_PATTERN =
      Pattern.compile("(\\d+)월 (\\d+)일 (오전|오후) (\\d+)시(?: (\\d+)분)?");

  public static LocalDateTime parseToLocalDateTime(String time) {
    Matcher matcher = DATETIME_PATTERN.matcher(time);
    // todo: 예외처리
    if (!matcher.matches()) throw new IllegalArgumentException();

    int year = LocalDateTime.now().getYear();
    int month = Integer.parseInt(matcher.group(1));
    int day = Integer.parseInt(matcher.group(2));
    int hour = Integer.parseInt(matcher.group(4));
    int minute = matcher.group(5) != null ? Integer.parseInt(matcher.group(5)) : 0;

    if ("오후".equals(matcher.group(3)) && hour != 12) hour += 12;
    else if ("오전".equals(matcher.group(3)) && hour == 12) hour = 0;

    return LocalDateTime.of(year, month, day, hour, minute);
  }
}

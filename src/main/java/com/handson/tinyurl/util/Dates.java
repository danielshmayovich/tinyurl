package com.handson.tinyurl.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.lang.Nullable;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class Dates {
    public static SimpleDateFormat shortDate = new SimpleDateFormat("yyyy-MM-dd"); // fixed format case
    public static TimeZone TIME_ZONE = TimeZone.getTimeZone("Asia/Jerusalem");
    public static SimpleDateFormat monthFormatter = new SimpleDateFormat("yyyy/MM");

    public Dates() {
    }

    public static String getCurMonth() {
        Date date = new Date();
        return monthFormatter.format(date);
    }

    public static String dateToStr(@Nullable LocalDate date) {
        return date == null ? null : shortDate.format(date.toDate());
    }

    public static Date atUtc(LocalDateTime date) {
        return atUtc(date, TIME_ZONE);
    }

    public static Date atUtc(LocalDateTime date, TimeZone zone) {
        if (date == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTimeZone(zone);
        calendar.set(date.getYear(), date.getMonthOfYear() - 1, date.getDayOfMonth()); // Month is 0-based in Calendar
        calendar.set(Calendar.HOUR_OF_DAY, date.getHourOfDay());
        calendar.set(Calendar.MINUTE, date.getMinuteOfHour());
        calendar.set(Calendar.SECOND, date.getSecondOfMinute());
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date atUtc(@Nullable LocalDate date) {
        return atUtc(date, TIME_ZONE);
    }

    public static Date atUtc(@Nullable LocalDate date, TimeZone zone) {
        return date == null ? null : atUtc(date.toLocalDateTime(LocalTime.MIDNIGHT), zone);
    }

    public static LocalDateTime atLocalTime(Date date) {
        return atLocalTime(date, TIME_ZONE);
    }

    public static LocalDateTime atLocalTime(Date date, TimeZone zone) {
        if (date == null) return null;
        OffsetDateTime localDate = OffsetDateTime.from(OffsetDateTime.ofInstant(date.toInstant(), zone.toZoneId()).toLocalDateTime());
        Calendar c = Calendar.getInstance();
        c.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        c.set(Calendar.HOUR_OF_DAY, localDate.getHour());
        c.set(Calendar.MINUTE, localDate.getMinute());
        c.set(Calendar.SECOND, localDate.getSecond());
        c.set(Calendar.MILLISECOND, 0);
        return LocalDateTime.fromCalendarFields(c);
    }

    public static Date nowUTC() {
        return DateTime.now().withZone(DateTimeZone.UTC).toDate();
    }

    public static String getFullDateTime() {
        return DateTime.now().withZone(DateTimeZone.UTC).toDateTimeISO().toString();
    }

    public static boolean equals(@Nullable Date date1, @Nullable Date date2) {
        if (date1 != null && date2 != null) {
            return date1.getTime() == date2.getTime();
        } else {
            return Objects.equals(date1, date2);
        }
    }
}
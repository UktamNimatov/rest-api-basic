package com.epam.esm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
//        System.out.println(LocalDateTime.now());
//        System.out.println(ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//        System.out.println();

        String regex = "[\\p{Alpha}\\s*+\\p{Digit}]{3,50}";
        System.out.println(Pattern.matches(regex, "Gy"));
    }
}

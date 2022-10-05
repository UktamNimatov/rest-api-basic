package com.epam.esm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        System.out.println(LocalDateTime.now());
        System.out.println(ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}

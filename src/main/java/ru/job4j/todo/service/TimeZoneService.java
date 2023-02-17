package ru.job4j.todo.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class TimeZoneService {
    private List<String> availableZones = new ArrayList<>();

    public TimeZoneService() {
        Collections.addAll(availableZones, TimeZone.getAvailableIDs());
    }

    public List<String> getAvailableZones() {
        return new ArrayList<>(availableZones);
    }

    public LocalDateTime getDateWithTimeZone(LocalDateTime date,  String zoneId) {
        return ZonedDateTime
                .of(date, ZoneId.of(TimeZone.getDefault().getID()))
                .withZoneSameInstant(ZoneId.of(zoneId)).toLocalDateTime();
    }

    public String getZoneIdOrDefault(String timeZoneId) {
        return "".equals(timeZoneId) ? TimeZone.getDefault().getID() : timeZoneId;
    }
}

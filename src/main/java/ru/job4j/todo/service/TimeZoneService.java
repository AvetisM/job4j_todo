package ru.job4j.todo.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
        return date.atZone(ZoneId.of(zoneId))
                .withZoneSameInstant(ZoneId.of(zoneId)).toLocalDateTime();
    }
}

package ru.job4j.todo.service;

import org.springframework.stereotype.Service;

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
}

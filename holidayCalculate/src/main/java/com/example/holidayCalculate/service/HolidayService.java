package com.example.holidayCalculate.service;

import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HolidayService {

    private List<LocalDate> holidays = new ArrayList<>(
        List.of(
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 1, 7),
            LocalDate.of(2024, 2, 23),
            LocalDate.of(2024, 3, 8), 
            LocalDate.of(2024, 5, 1), 
            LocalDate.of(2024, 5, 9),
            LocalDate.of(2024, 6, 12),
            LocalDate.of(2024, 11, 4)
        )
    );

    public boolean isHoliday(LocalDate date) {
        return holidays.contains(date);
    }

    public void addHoliday(LocalDate date) {
        if (!holidays.contains(date)) {
            holidays.add(date);
        }
    }

    public void removeHoliday(LocalDate date) {
        holidays.remove(date);
    }

    public boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public boolean isWorkingDay(LocalDate date) {
        return !(isWeekend(date) || isHoliday(date));
    }

    public List<LocalDate> getHolidays() {
        return holidays;
    }
}

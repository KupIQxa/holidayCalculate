package com.example.holidayCalculate.controller;

import com.example.holidayCalculate.service.HolidayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
public class HolidayCalculatorController {

    private final HolidayService holidayService;

    public HolidayCalculatorController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping("/calculate")
    public Map<String, Object> calculateVacationPay(@RequestParam(required = false) Double averageSalary, 
                                                    @RequestParam(required = false) Integer vacationDays, 
                                                    @RequestParam(required = false) List<String> vacationDates) {
        if (averageSalary == null || vacationDays == null) {
            throw new IllegalArgumentException("Средняя зарплата и количество отпускных дней должны быть указаны.");
        }
        
        if (averageSalary <= 0 || vacationDays <= 0) {
            throw new IllegalArgumentException("Средняя зарплата и количество отпускных дней должны быть положительными.");
        }

        double dailyRate = averageSalary / 29.3;
        long paidDays = vacationDays;
        List<LocalDate> paidDates = new ArrayList<>();

        try {
            if (vacationDates != null && !vacationDates.isEmpty()) {
                List<LocalDate> dates = vacationDates.stream()
                                            .map(LocalDate::parse)
                                            .collect(Collectors.toList());
                
                paidDates = dates.stream()
                                 .filter(holidayService::isWorkingDay)
                                 .collect(Collectors.toList());

                long paidDatesCount = paidDates.size();

                if (vacationDays > paidDatesCount) {
                    LocalDate lastVacationDate = dates.get(dates.size() - 1);
                    while (paidDatesCount < vacationDays) {
                        lastVacationDate = lastVacationDate.plusDays(1);
                        if (holidayService.isWorkingDay(lastVacationDate)) {
                            paidDates.add(lastVacationDate);
                            paidDatesCount++;
                        }
                    }
                }

                paidDays = paidDates.size();
            }

            double totalPay = dailyRate * paidDays;

            Map<String, Object> response = new HashMap<>();
            response.put("Общая сумма отпускных", totalPay);
            response.put("Среднедневной заработок", dailyRate);
            response.put("Количество оплачиваемых дней", paidDays);
            response.put("Оплаченные даты", paidDates.stream().map(LocalDate::toString).collect(Collectors.toList()));

            return response;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Некорректный формат даты.");
        }
    }
}

package com.example.holidayCalculate.dto;

import java.util.List;

public class VacationPayResponse {
    private double totalPay;
    private double dailyRate;
    private long paidDays;
    private List<String> paidDates;

    public VacationPayResponse(double totalPay, double dailyRate, long paidDays, List<String> paidDates) {
        this.totalPay = totalPay;
        this.dailyRate = dailyRate;
        this.paidDays = paidDays;
        this.paidDates = paidDates;
    }

    public double getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(double totalPay) {
        this.totalPay = totalPay;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public long getPaidDays() {
        return paidDays;
    }

    public void setPaidDays(long paidDays) {
        this.paidDays = paidDays;
    }

    public List<String> getPaidDates() {
        return paidDates;
    }

    public void setPaidDates(List<String> paidDates) {
        this.paidDates = paidDates;
    }
}

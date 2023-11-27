package br.com.beautypro.payload.request;

public class MonthlyServiceSumDTO {
    private int month;
    private int year;
    private double totalSum;

    public MonthlyServiceSumDTO() {
    }

    public MonthlyServiceSumDTO(int month, int year, double totalSum) {
        this.month = month;
        this.year = year;
        this.totalSum = totalSum;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }
}

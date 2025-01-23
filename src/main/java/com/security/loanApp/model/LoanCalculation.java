package com.security.loanApp.model;

import jakarta.persistence.*;


import java.util.List;

@Entity
public class LoanCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private double principal;

    private double annualRate;

    private int years;

    private String loanType;// "amortized", "deferred", "bond"

    private int defermentMonths;

    private double bondCouponRate;

    private double resultValue;

    private int totalMonths;

    @ElementCollection // Marks schedule as a collection of embeddable types
    @CollectionTable(name = "schedule_row", joinColumns = @JoinColumn(name = "loan_calculation_id"))
    private List<ScheduleRow> schedule;

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(double annualRate) {
        this.annualRate = annualRate;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public int getDefermentMonths() {
        return defermentMonths;
    }

    public void setDefermentMonths(int defermentMonths) {
        this.defermentMonths = defermentMonths;
    }

    public double getBondCouponRate() {
        return bondCouponRate;
    }

    public void setBondCouponRate(double bondCouponRate) {
        this.bondCouponRate = bondCouponRate;
    }

    public double getResultValue() {
        return resultValue;
    }

    public void setResultValue(double resultValue) {
        this.resultValue = resultValue;
    }

    public int getTotalMonths() {
        return totalMonths;
    }

    public void setTotalMonths(int totalMonths) {
        this.totalMonths = totalMonths;
    }

    public List<ScheduleRow> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleRow> schedule) {
        this.schedule = schedule;
    }



    @Embeddable
    public static class ScheduleRow {
        private int payMonth;
        private double payment;
        private double principal;
        private double interest;
        private double remainingBalance;


        public int getMonth() {
            return payMonth;
        }

        public void setMonth(int payMonth) {
            this.payMonth = payMonth;
        }

        public double getPayment() {
            return payment;
        }

        public void setPayment(double payment) {
            this.payment = payment;
        }

        public double getPrincipal() {
            return principal;
        }

        public void setPrincipal(double principal) {
            this.principal = principal;
        }

        public double getInterest() {
            return interest;
        }

        public void setInterest(double interest) {
            this.interest = interest;
        }

        public double getRemainingBalance() {
            return remainingBalance;
        }

        public void setRemainingBalance(double remainingBalance) {
            this.remainingBalance = remainingBalance;
        }

    }

}

package com.security.loanApp.service;

import com.security.loanApp.model.LoanCalculation;
import com.security.loanApp.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public LoanCalculation calculateLoan(LoanCalculation request) {
        double resultValue = 0;
        int totalMonths = 0;

        switch (request.getLoanType().toLowerCase()) {
            case "amortized":
                resultValue = calculateAmortizedLoan(request);
                totalMonths = request.getYears() * 12;
                break;
            case "deferred":
                resultValue = calculateDeferredLoan(request);
                totalMonths = request.getYears() * 12;
                break;
            case "bond":
                resultValue = calculateBond(request);
                totalMonths = request.getYears();
                break;
            default:
                throw new IllegalArgumentException("Invalid loan type");
        }

        request.setResultValue(resultValue);
        request.setTotalMonths(totalMonths);

        // Save to the database
        return loanRepository.save(request);
    }

    private double calculateAmortizedLoan(LoanCalculation request) {
        double monthlyRate = request.getAnnualRate() / 100 / 12;
        int months = request.getYears() * 12;
        double denominator = Math.pow(1 + monthlyRate, months) - 1;
        return (request.getPrincipal() * monthlyRate * Math.pow(1 + monthlyRate, months)) / denominator;
    }

    private double calculateDeferredLoan(LoanCalculation request) {
        int defermentMonths = request.getDefermentMonths();
        double monthlyRate = request.getAnnualRate() / 100 / 12;
        double deferredAmount = request.getPrincipal() * Math.pow(1 + monthlyRate, defermentMonths);
        int remainingMonths = (request.getYears() * 12) - defermentMonths;
        double denominator = Math.pow(1 + monthlyRate, remainingMonths) - 1;
        return (deferredAmount * monthlyRate * Math.pow(1 + monthlyRate, remainingMonths)) / denominator;
    }

    private double calculateBond(LoanCalculation request) {
        double annualRate = request.getAnnualRate() / 100;
        double couponPayment = request.getBondCouponRate() * request.getPrincipal(); // Principal used for bonds
        int totalPeriods = request.getYears();

        double presentValue = 0;
        for (int t = 1; t <= totalPeriods; t++) {
            presentValue += couponPayment / Math.pow(1 + annualRate, t);
        }
        presentValue += request.getPrincipal() / Math.pow(1 + annualRate, totalPeriods);

        return presentValue;
    }

    public List<LoanCalculation> getCalculationHistory() {

        return loanRepository.findAll();
    }
}

package com.security.loanApp.controller;


import com.security.loanApp.model.LoanCalculation;
import com.security.loanApp.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@CrossOrigin(origins = "http://localhost:5174")
public class LoanController {

    @Autowired
    private LoanService loanService;


    @PostMapping("/calculate")
    public LoanCalculation calculateLoan(@RequestBody LoanCalculation request) {
        return loanService.calculateLoan(request);
    }

    @GetMapping("/history")
    public List<LoanCalculation> getCalculationHistory() {
        return loanService.getCalculationHistory();
    }

}

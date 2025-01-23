package com.security.loanApp.repository;


import com.security.loanApp.model.LoanCalculation;
import com.security.loanApp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<LoanCalculation,Long> {

}

package org.launchcode.expense_tracker.controllers;

import org.launchcode.expense_tracker.models.Expense;
import org.launchcode.expense_tracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sort")
public class SortExpenseControllerApi {

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/by-date")
    public List<Expense> getExpensesByMonthAndYearSortedByDate(
            @RequestParam int month,
            @RequestParam int year
    ) {
        return expenseRepository.findByMonthAndYearSortedByDateDesc(month, year);
    }

    @GetMapping("/by-amount")
    public List<Expense> getExpensesByMonthAndYearSortedByAmount(
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam String sortOrder
    ) {
        if ("asc".equalsIgnoreCase(sortOrder)) {
            return expenseRepository.findByMonthAndYearSortedByAmountAsc(month, year);
        } else {
            return expenseRepository.findByMonthAndYearSortedByAmountDesc(month, year);
        }
    }
}


package org.launchcode.expense_tracker.controllers;

import org.launchcode.expense_tracker.models.Expense;
import org.launchcode.expense_tracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/expenses")
public class ViewExpenseByDateController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/yearly")
    public ResponseEntity<Map<String, Double>> getYearlyExpenses(@RequestParam Long userId, @RequestParam String year) {
        LocalDate startDate = LocalDate.parse(year + "-01-01");
        LocalDate endDate = LocalDate.parse(year + "-12-31");
        List<Expense> expenses = expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
        double totalExpense = expenses.stream().mapToDouble(Expense::getAmount).sum();
        Map<String, Double> result = new HashMap<>();
        result.put("totalExpense", totalExpense);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/monthly")
    public ResponseEntity<Map<String, Double>> getMonthlyExpenses(@RequestParam Long userId, @RequestParam String year, @RequestParam String month) {
        LocalDate startDate = LocalDate.parse(year + "-" + month + "-01");
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        List<Expense> expenses = expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
        double totalExpense = expenses.stream().mapToDouble(Expense::getAmount).sum();
        Map<String, Double> result = new HashMap<>();
        result.put("totalExpense", totalExpense);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/daily")
    public ResponseEntity<Map<String, Double>> getDailyExpenses(@RequestParam Long userId, @RequestParam String year, @RequestParam String month, @RequestParam String day) {
        LocalDate date = LocalDate.parse(year + "-" + month + "-" + day);
        List<Expense> expenses = expenseRepository.findByUserIdAndDate(userId, date);
        double totalExpense = expenses.stream().mapToDouble(Expense::getAmount).sum();
        Map<String, Double> result = new HashMap<>();
        result.put("totalExpense", totalExpense);
        return ResponseEntity.ok(result);
    }
}

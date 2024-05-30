package org.launchcode.expense_tracker.controllers;

import org.launchcode.expense_tracker.models.Expense;
import org.launchcode.expense_tracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/expenses")
public class ViewExpenseByDateController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/yearly")
    public ResponseEntity<List<Map<String, Object>>> getYearlyExpenses(@RequestParam Long userId, @RequestParam String year) {
        LocalDate startDate = LocalDate.parse(year + "-01-01");
        LocalDate endDate = LocalDate.parse(year + "-12-31");
        List<Expense> expenses = expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
        double totalExpense = expenses.stream().mapToDouble(Expense::getAmount).sum();

        // Create a list to hold the yearly expenses data
        List<Map<String, Object>> yearlyExpensesData = new ArrayList<>();

        // Create a map for the current year's data
        Map<String, Object> yearlyExpenseData = new HashMap<>();
        yearlyExpenseData.put("year", year);
        yearlyExpenseData.put("totalExpense", totalExpense);

        // Add the current year's data to the list
        yearlyExpensesData.add(yearlyExpenseData);

        return ResponseEntity.ok(yearlyExpensesData);
    }

    @GetMapping("/monthly")
    public ResponseEntity<Map<String, Double>> getMonthlyExpenses(
            @RequestParam Long userId,
            @RequestParam String year,
            @RequestParam int month) {

        String formattedMonth = String.format("%02d", month); // Ensures month is always two digits
        LocalDate startDate = LocalDate.parse(year + "-" + formattedMonth + "-01");
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        List<Expense> expenses = expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
        double totalExpense = expenses.stream().mapToDouble(Expense::getAmount).sum();
        Map<String, Double> result = new HashMap<>();
        result.put("totalExpense", totalExpense);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/daily")
    public ResponseEntity<Map<String, Double>> getDailyExpenses(
            @RequestParam Long userId,
            @RequestParam String year,
            @RequestParam String month,
            @RequestParam String day) {
        try {
            String dateString = String.format("%s-%02d-%02d", year, Integer.parseInt(month), Integer.parseInt(day));
            LocalDate date = LocalDate.parse(dateString);
            List<Expense> expenses = expenseRepository.findByUserIdAndDate(userId, date);
            double totalExpense = expenses.stream().mapToDouble(Expense::getAmount).sum();
            Map<String, Double> result = new HashMap<>();
            result.put("totalExpense", totalExpense);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    
    @GetMapping("/for-date")
    public ResponseEntity<List<Expense>> getExpensesForDate(
            @RequestParam Long userId,
            @RequestParam String year,
            @RequestParam String month,
            @RequestParam String day) {
        try {
            String dateString = String.format("%s-%02d-%02d", year, Integer.parseInt(month), Integer.parseInt(day));
            LocalDate date = LocalDate.parse(dateString);
            List<Expense> expenses = expenseRepository.findByUserIdAndDate(userId, date);
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

package org.launchcode.expense_tracker.controllers;


import org.launchcode.expense_tracker.models.Expense;
import org.launchcode.expense_tracker.models.User;
import org.launchcode.expense_tracker.repository.ExpenseRepository;
import org.launchcode.expense_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/expense")
public class ExpenseControllerApi {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/add")
    public Expense addExpense(@RequestParam Long id, @RequestBody Expense expense) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            expense.setUser(user);
            return expenseRepository.save(expense);
        } else {
            return null;
        }
    }

    @GetMapping("/edit/{expenseId}")
    public Object getExpenseDetailsForEditing(
            @PathVariable Long expenseId,
            @RequestParam Long id) {

        Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);

        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();


            if (expense.getUser().getId().equals(id)) {

                return expense;
            } else {

                return "user not found";
            }
        } else {

            return "Expense not found";
        }
    }
}

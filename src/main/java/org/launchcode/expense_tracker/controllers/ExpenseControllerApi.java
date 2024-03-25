package org.launchcode.expense_tracker.controllers;


import org.launchcode.expense_tracker.models.Expense;
import org.launchcode.expense_tracker.models.User;
import org.launchcode.expense_tracker.repository.ExpenseRepository;
import org.launchcode.expense_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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


    @PutMapping("/edit/{expenseId}")
    public Expense updateExpense(@PathVariable Long expenseId, @RequestParam Long id, @RequestBody Expense updatedExpense) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);

            if (optionalExpense.isPresent()) {
                Expense existingExpense = optionalExpense.get();
                existingExpense.setDescription(updatedExpense.getDescription());
                existingExpense.setDate(updatedExpense.getDate());
                existingExpense.setAmount(updatedExpense.getAmount());
                existingExpense.setCategory(updatedExpense.getCategory());
                existingExpense.setUser(user);
                return expenseRepository.save(existingExpense);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    @GetMapping("/list")
    public Iterable<Expense> getExpenseForUser(@RequestParam Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return expenseRepository.findByUser(user);
        } else {

            return Collections.emptyList();
        }
    }

    @DeleteMapping("/{expenseId}")
    public void deleteExpense(@PathVariable Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }


}

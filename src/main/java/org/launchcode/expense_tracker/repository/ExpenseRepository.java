package org.launchcode.expense_tracker.repository;

import org.launchcode.expense_tracker.models.Expense;
import org.launchcode.expense_tracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUser(User user);
}


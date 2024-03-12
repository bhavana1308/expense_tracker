package org.launchcode.expense_tracker.repository;

import org.launchcode.expense_tracker.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}


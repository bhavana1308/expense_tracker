package org.launchcode.expense_tracker.repository;

import org.launchcode.expense_tracker.models.Expense;
import org.launchcode.expense_tracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUser(User user);

    List<Expense> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    List<Expense> findByUserIdAndDate(Long userId, LocalDate date);

    @Query("SELECT e FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month ORDER BY e.date DESC")
    List<Expense> findByMonthAndYearSortedByDateDesc(@Param("month") int month, @Param("year") int year);

    @Query("SELECT e FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month ORDER BY e.amount DESC")
    List<Expense> findByMonthAndYearSortedByAmountDesc(@Param("month") int month, @Param("year") int year);

    @Query("SELECT e FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month ORDER BY e.amount ASC")
    List<Expense> findByMonthAndYearSortedByAmountAsc(@Param("month") int month, @Param("year") int year);
}





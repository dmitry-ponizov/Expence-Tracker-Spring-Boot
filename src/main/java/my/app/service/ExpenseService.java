package my.app.service;

import my.app.model.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    List<Expense> getExpenseByDay(String date);
    List<Expense> getExpenseByCategoryAndMonth(String category, String month);

    List<String> getAllExpenseCategories();

    Optional<Expense> getExpenseById(long id);

    Expense addExpense(Expense expense);

    boolean updateExpense(Expense expense);

    boolean deleteExpense(long id);
}

package my.app.service;

import my.app.model.Expense;
import my.app.repository.ExpenseRepository;
import my.app.utils.ExpenseDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile("db")
public class ExpenseServiceImlDb implements ExpenseService {


    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImlDb(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> getExpenseByDay(String date) {
        return expenseRepository.findAll().stream()
                .filter(expense -> expense.getDate().equalsIgnoreCase(date))
                .toList();
    }

    @Override
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month) {
        return expenseRepository.findAll().stream()
                .filter(expense ->  expense.getCategory().equalsIgnoreCase(category) && expense.getDate().startsWith(month))
                .toList();
    }

    @Override
    public List<String> getAllExpenseCategories() {
        return expenseRepository.findAll().stream()
                .map(Expense::getCategory)
                .distinct()
                .toList();
    }

    @Override
    public Optional<Expense> getExpenseById(long id) {
        return expenseRepository.findById(id);

    }

    @Override
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public boolean updateExpense(Expense expense) {
        Optional<Expense> existingExpense = expenseRepository.findById(expense.getId());

        if (existingExpense.isPresent()) {
            expenseRepository.save(expense);
            return true;
        }

       return false;

    }


    @Override
    public boolean deleteExpense(long id) {
        Optional<Expense> existingExpense = expenseRepository.findById(id);
        if (existingExpense.isPresent()) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

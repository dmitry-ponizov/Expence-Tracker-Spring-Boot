package my.app.service;

import my.app.model.Expense;
import my.app.utils.ExpenseDataLoader;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Profile("json")
public class ExpenseServiceImpl implements ExpenseService {

    private static final AtomicLong idCounter = new AtomicLong();

    @Override
    public List<Expense> getExpenseByDay(String date) {
        return ExpenseDataLoader.getExpesnes()
                .stream()
                .filter(expense ->  expense.getDate().equalsIgnoreCase(date))
                .toList();
    }

    @Override
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month) {
        return ExpenseDataLoader.getExpesnes()
                .stream()
                .filter(expense ->  expense.getCategory().equalsIgnoreCase(category) && expense.getDate().startsWith(month))
                .toList();
    }

    @Override
    public List<String> getAllExpenseCategories() {
        return ExpenseDataLoader.getExpesnes()
                    .stream()
                    .map(Expense::getCategory)
                    .distinct()
                    .toList();
        }

    @Override
    public Optional<Expense> getExpenseById(long id) {
        return ExpenseDataLoader.getExpesnes().stream().filter(expense -> expense.getId() == id).findFirst();
    }

    @Override
    public Expense addExpense(Expense expense) {
         expense.setId(idCounter.incrementAndGet());
         ExpenseDataLoader.getExpesnes().add(expense);
         return expense;
    }

    @Override
    public boolean updateExpense(Expense expense) {
      Optional<Expense> existingExpense = getExpenseById(expense.getId());

        if(existingExpense.isPresent()) {
           ExpenseDataLoader.getExpesnes().remove(existingExpense.get());
           ExpenseDataLoader.getExpesnes().add(expense);
           return true;
        }
        return false;

    }

    @Override
    public boolean deleteExpense(long id) {
        Optional<Expense> existingExpense = getExpenseById(id);
        if(existingExpense.isPresent()) {
            ExpenseDataLoader.getExpesnes().remove(existingExpense.get());
            return true;
        }
        return false;
    }
}

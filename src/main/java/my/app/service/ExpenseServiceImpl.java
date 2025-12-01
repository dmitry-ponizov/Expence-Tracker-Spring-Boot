package my.app.service;

import my.app.model.Expense;
import my.app.model.User;
import my.app.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private static final AtomicLong idCounter = new AtomicLong();
    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;
    }

    @Override
    public List<Expense> getAllExpenses(Long userId) {
        return new ArrayList<>(expenseRepository.findByUserIdOrderByDateDesc(userId));
    }

    @Override
    public List<Expense> getExpenseByDay(String date, Long userId) {
        return expenseRepository.findByUserIdOrderByDateDesc(userId)
                .stream()
                .filter(expense -> expense.getDate().equals(date))
                .toList();
    }

    @Override
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month, Long userId) {

        return expenseRepository.findByUserIdOrderByDateDesc(userId)
                .stream()
                .filter(expense ->  expense.getCategory().equalsIgnoreCase(category) && expense.getDate().startsWith(month))
                .toList();
    }

    @Override
    public List<String> getAllExpenseCategories(Long userId) {
        return expenseRepository.findByUserIdOrderByDateDesc(userId)
                    .stream()
                    .map(Expense::getCategory)
                    .distinct()
                    .toList();
        }

    @Override
    public Optional<Expense> getExpenseById(Long id, Long userId) {
        return expenseRepository.findByIdAndUserId(id,userId).stream().filter(expense -> expense.getId() == id).findFirst();
    }

    @Override
    public Expense addExpense(Expense expense, Long userId) {
        Optional<User> optionalUser = userService.findUserById(userId);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            expense.setUser(user);
            return expenseRepository.save(expense);
        } else {
             throw new IllegalArgumentException("User not found");
        }

    }

    @Override
    public boolean updateExpense(Expense expense, Long userId) {
      Optional<Expense> existingExpense = expenseRepository.findByIdAndUserId(expense.getId(), userId);

        if(existingExpense.isPresent()) {
           expense.setUser(existingExpense.get().getUser());
           expenseRepository.save(expense);
           return true;
        }
        return false;

    }

    @Override
    public boolean deleteExpense(Long id, Long userId) {
        Optional<Expense> existingExpense = expenseRepository.findByIdAndUserId(id, userId);
        if(existingExpense.isPresent()) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }

}

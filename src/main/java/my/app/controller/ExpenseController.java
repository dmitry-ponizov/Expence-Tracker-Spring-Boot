package my.app.controller;

import my.app.model.Expense;
import my.app.model.User;
import my.app.service.ExpenseService;
import my.app.service.ExpenseServiceImpl;
import my.app.service.UserService;
import my.app.utils.ExpenseDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ExpenseController {


    private final ExpenseService expenseService;
    private final UserService userService;


    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }


    @GetMapping("/expenses/categories")
    public ResponseEntity<List<String>> getExpenseCategories(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<String> categories = expenseService.getAllExpenseCategories(user.getId());

        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(categories);
        }
    }

    @GetMapping("/expenses/day/{date}")
    public ResponseEntity<List<Expense>> getExpensesByDay(@PathVariable("date") String date, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<Expense> expenses = expenseService.getExpenseByDay(date, user.getId());
        return ResponseEntity.ok(expenses);
    }


    @GetMapping("/expenses/category/{category}/month")
    public ResponseEntity<List<Expense>> getExpensesByCategoryAndMonth(
            @PathVariable("category") String category,
            @RequestParam("month") String month,
            Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(expenseService.getExpenseByCategoryAndMonth(category, month, user.getId()));

    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Optional<Expense>> getExpenseById(@PathVariable("id") long id, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(expenseService.getExpenseById(id,user.getId()));
    }

    @PostMapping("/expenses")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Expense createdExpense = expenseService.addExpense(expense,user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable("id") Long id, @RequestBody Expense expense, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        expense.setId(id);
        boolean isUpdated = expenseService.updateExpense(expense, user.getId());
        if (isUpdated) {
            return new ResponseEntity<Expense>(expense, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable("id") Long id, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        boolean isDeleted = expenseService.deleteExpense(id,user.getId());
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

package my.app.controller;

import my.app.model.Expense;
import my.app.service.ExpenseService;
import my.app.service.ExpenseServiceImpl;
import my.app.utils.ExpenseDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ExpenseController {


    private final ExpenseService expenseService;


    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    @GetMapping("/expenses/categories")
    public ResponseEntity<List<String>> getExpenseCategories() {
        List<String> categories = expenseService.getAllExpenseCategories();

        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(categories);
        }
    }

    @GetMapping("/expenses/day/{date}")
    public ResponseEntity<List<Expense>> getExpensesByDay(@PathVariable("date") String date) {
        return ResponseEntity.ok(expenseService.getExpenseByDay(date));
    }


    @GetMapping("/expenses/category/{category}/month")
    public ResponseEntity<List<Expense>> getExpensesByCategoryAndMonth(
            @PathVariable("category") String category,
            @RequestParam("month") String month) {
        return ResponseEntity.ok(expenseService.getExpenseByCategoryAndMonth(category, month));

    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Optional<Expense>> getExpenseById(@PathVariable("id") long id) {
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @PostMapping("/expenses")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        Expense createdExpense = expenseService.addExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable("id") Long id, @RequestBody Expense expense) {
        expense.setId(id);
        boolean isUpdated = expenseService.updateExpense(expense);
        if (isUpdated) {
            return new ResponseEntity<Expense>(expense, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable("id") Long id) {
        boolean isDeleted = expenseService.deleteExpense(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

package my.app;


import my.app.model.Expense;
import my.app.utils.ExpenseDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
 class Main implements CommandLineRunner {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Expense> expenseList = ExpenseDataLoader.getExpesnes();
        expenseList.forEach(System.out::println);
    }
}
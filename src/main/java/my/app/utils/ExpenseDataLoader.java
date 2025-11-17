package my.app.utils;

import jakarta.annotation.PostConstruct;
import my.app.model.Expense;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Component
public class ExpenseDataLoader {

    private  static List<Expense> expesnes = new ArrayList<>();

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getResourceAsStream("/expenses.json");

        expesnes = mapper.readValue(is, new TypeReference<List<Expense>>() {
        });
    }

    public static List<Expense> getExpesnes() {
        return expesnes;
    }
}

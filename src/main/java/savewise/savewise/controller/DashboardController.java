package savewise.savewise.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import savewise.savewise.entity.FinancialTransaction;
import savewise.savewise.entity.TransactionType;
import savewise.savewise.entity.User;
import savewise.savewise.repository.CategoryRepository;
import savewise.savewise.repository.FinancialTransactionRepository;
import savewise.savewise.repository.UserRepository;

@Controller
public class DashboardController {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FinancialTransactionRepository transactionRepository;

    public DashboardController(
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            FinancialTransactionRepository transactionRepository) {

        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

        @GetMapping("/dashboard")
        public String dashboard(
                Model model,
                Principal principal) {

        User user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow();

        var transactions = transactionRepository.findByUser(user);

        double income = transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(FinancialTransaction::getAmount)
                .sum();

        double expenses = transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(FinancialTransaction::getAmount)
                .sum();

        double balance = income - expenses;

        model.addAttribute("income", income);
        model.addAttribute("expenses", expenses);
        model.addAttribute("balance", balance);

        model.addAttribute(
                "totalCategories",
                categoryRepository.findByUser(user).size());

        model.addAttribute(
                "totalTransactions",
                transactions.size());

        return "dashboard";
        }
}
package savewise.savewise.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

        model.addAttribute(
                "totalCategories",
                categoryRepository.findByUser(user).size());

        model.addAttribute(
                "totalTransactions",
                transactionRepository.findByUser(user).size());

        return "dashboard";
    }
}
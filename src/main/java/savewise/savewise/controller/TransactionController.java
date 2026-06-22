package savewise.savewise.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import savewise.savewise.entity.FinancialTransaction;
import savewise.savewise.entity.TransactionType;
import savewise.savewise.entity.User;
import savewise.savewise.repository.CategoryRepository;
import savewise.savewise.repository.FinancialTransactionRepository;
import savewise.savewise.repository.UserRepository;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final FinancialTransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TransactionController(
            FinancialTransactionRepository transactionRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository) {

        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String listTransactions(
            Model model,
            Principal principal) {

        User user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow();

        model.addAttribute(
                "transactions",
                transactionRepository.findByUser(user));

        return "transactions/list";
    }

    @GetMapping("/new")
    public String newTransaction(
            Model model,
            Principal principal) {

        User user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow();

        var categories = categoryRepository.findByUser(user);

        if (categories.isEmpty()) {
            return "redirect:/categories/new";
        }

        model.addAttribute(
                "transaction",
                new FinancialTransaction());

        model.addAttribute(
                "categories",
                categories);

        model.addAttribute(
                "types",
                TransactionType.values());

        return "transactions/form";
    }

    @PostMapping("/save")
    public String saveTransaction(
            @Valid @ModelAttribute("transaction")
            FinancialTransaction transaction,
            BindingResult result,
            Principal principal,
            Model model) {

        User user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow();

        if(result.hasErrors()) {

            model.addAttribute(
                    "categories",
                    categoryRepository.findByUser(user));

            model.addAttribute(
                    "types",
                    TransactionType.values());

            return "transactions/form";
        }

        transaction.setUser(user);

        transactionRepository.save(transaction);

        return "redirect:/transactions";
    }

    @GetMapping("/delete/{id}")
    public String deleteTransaction(
            @PathVariable Long id) {

        transactionRepository.deleteById(id);

        return "redirect:/transactions";
    }
}
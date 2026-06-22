package savewise.savewise.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import savewise.savewise.entity.SavingsGoal;
import savewise.savewise.entity.User;
import savewise.savewise.repository.SavingsGoalRepository;
import savewise.savewise.repository.UserRepository;

@Controller
@RequestMapping("/goals")
public class SavingsGoalController {

    private final SavingsGoalRepository goalRepository;
    private final UserRepository userRepository;

    public SavingsGoalController(
            SavingsGoalRepository goalRepository,
            UserRepository userRepository) {

        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }
    @GetMapping
    public String listGoals(
            Model model,
            Principal principal) {

        User user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow();

        model.addAttribute(
                "goals",
                goalRepository.findByUser(user));

        return "goals/list";
    }
    @GetMapping("/new")
    public String newGoal(
            Model model) {

        model.addAttribute(
                "goal",
                new SavingsGoal());

        return "goals/form";
    }
    @PostMapping("/save")
    public String saveGoal(
            @Valid SavingsGoal goal,
            BindingResult result,
            Model model,
            Principal principal) {

        if (result.hasErrors()) {

            model.addAttribute("goal", goal);

            return "goals/form";
        }

        User user = userRepository
                .findByUsername(principal.getName())
                .orElseThrow();

        goal.setUser(user);

        if (goal.getCurrentAmount() == null) {
            goal.setCurrentAmount(0.0);
        }

        goalRepository.save(goal);

        return "redirect:/goals";
    }
    @GetMapping("/delete/{id}")
    public String deleteGoal(
            @PathVariable Long id) {

        goalRepository.deleteById(id);

        return "redirect:/goals";
    }

    @GetMapping("/add/{id}")
public String addMoney(@PathVariable Long id){

    SavingsGoal goal = goalRepository.findById(id)
            .orElseThrow();

    goal.setCurrentAmount(goal.getCurrentAmount() + 100);

    goalRepository.save(goal);

    return "redirect:/goals";
}

    @GetMapping("/remove/{id}")
    public String removeMoney(@PathVariable Long id){

        SavingsGoal goal = goalRepository.findById(id)
                .orElseThrow();

        double newAmount = goal.getCurrentAmount() - 100;

        if(newAmount < 0){
            newAmount = 0;
        }

        goal.setCurrentAmount(newAmount);

        goalRepository.save(goal);

        return "redirect:/goals";
    }

    @GetMapping("/update/{id}")
public String showUpdateForm(
        @PathVariable Long id,
        Model model) {

    SavingsGoal goal = goalRepository
            .findById(id)
            .orElseThrow();

    model.addAttribute("goal", goal);

    return "goals/update";
}

    @PostMapping("/update/{id}")
    public String updateGoalAmount(
            @PathVariable Long id,
            Double amount,
            String action) {

        SavingsGoal goal = goalRepository
                .findById(id)
                .orElseThrow();

        if ("add".equals(action)) {

            goal.setCurrentAmount(
                    goal.getCurrentAmount() + amount);

        } else if ("remove".equals(action)) {

            double newAmount =
                    goal.getCurrentAmount() - amount;

            goal.setCurrentAmount(
                    Math.max(0, newAmount));
        }

        goalRepository.save(goal);

        return "redirect:/goals";
    }
}

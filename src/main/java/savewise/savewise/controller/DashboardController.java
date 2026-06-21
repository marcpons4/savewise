package savewise.savewise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("income", 0);
        model.addAttribute("expenses", 0);
        model.addAttribute("balance", 0);

        return "dashboard";
    }
}
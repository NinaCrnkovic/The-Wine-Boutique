package hr.algebra.thewineboutique.controller.mvc;
import hr.algebra.thewineboutique.publisher.CustomSpringEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hr.algebra.thewineboutique.dto.UserRegistrationDto;
import hr.algebra.thewineboutique.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@AllArgsConstructor
@RequestMapping("/TheWineBoutique")
public class RegistrationController {

    private final UserDetailsServiceImpl userDetailsService;
    private final CustomSpringEventPublisher eventPublisher;

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto, RedirectAttributes redirectAttributes) {
        userDetailsService.save(registrationDto);
        eventPublisher.publishCustomEvent("User registered: " + registrationDto.getUsername());
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }
}
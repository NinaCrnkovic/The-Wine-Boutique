package hr.algebra.thewineboutique.controller.mvc;

import hr.algebra.thewineboutique.service.CartService;
import hr.algebra.thewineboutique.service.LoginLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/TheWineBoutique")
public class LoginLogController extends BaseController{


    private LoginLogService loginLogService;

    public LoginLogController(CartService cartService, LoginLogService loginLogService) {
        super(cartService);
        this.loginLogService = loginLogService;
    }

    @GetMapping("/loginLogs")
    public String getAllLoginLogs(Model model) {
        model.addAttribute("loginLogs", loginLogService.getAllLoginLogs());
        return "loginLogs";
    }
}

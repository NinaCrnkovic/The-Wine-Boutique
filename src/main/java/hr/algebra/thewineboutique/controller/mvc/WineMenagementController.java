package hr.algebra.thewineboutique.controller.mvc;

import hr.algebra.thewineboutique.model.Wine;
import hr.algebra.thewineboutique.model.WineCategoryEnum;
import hr.algebra.thewineboutique.service.CartService;
import hr.algebra.thewineboutique.service.WineService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/TheWineBoutique")

public class WineMenagementController extends BaseController {

    private WineService wineService;

    public WineMenagementController(CartService cartService, WineService wineService) {
        super(cartService);
        this.wineService = wineService;
    }


    @GetMapping("/newWine")
    public String getNewWinePage(Model model) {
        model.addAttribute("wineCategoryList", WineCategoryEnum.values());
        model.addAttribute("wine", new Wine());
        return "newWine";
    }

    @PostMapping("/newWine")
    public String saveNewWine(Wine wine, Model model) {
        wineService.save(wine);

        return "success";
    }

    @GetMapping("/edit/{id}")
    public String getEditWinePage(@PathVariable int id, Model model) {
        Wine wine = wineService.findById(id);
        model.addAttribute("wineCategoryList", WineCategoryEnum.values());
        model.addAttribute("wine", wine);
        return "editWine";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteConfirmationPage(@PathVariable int id, Model model) {
        Wine wine = wineService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String);

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("wine", wine);
        return "confirmDelete";
    }

    @PostMapping("/delete/{id}")
    public String deleteWine(@PathVariable int id) {
        wineService.delete(id);
        return "redirect:/TheWineBoutique/allwines";
    }

}

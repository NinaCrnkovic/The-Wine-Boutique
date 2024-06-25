package hr.algebra.thewineboutique.controller.mvc;


import hr.algebra.thewineboutique.model.Wine;
import hr.algebra.thewineboutique.service.CartService;
import hr.algebra.thewineboutique.service.WineService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/TheWineBoutique")

public class WineController extends BaseController {

    private WineService wineService;

    public WineController(CartService cartService, WineService wineService) {
        super(cartService);
        this.wineService = wineService;
    }


    @GetMapping("/allwines")
    public String getAllWines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Page<Wine> winePage = wineService.findAll(PageRequest.of(page, size));

        model.addAttribute("winePage", winePage);
        model.addAttribute("currentPage", page);
        return "wineList";
    }


}
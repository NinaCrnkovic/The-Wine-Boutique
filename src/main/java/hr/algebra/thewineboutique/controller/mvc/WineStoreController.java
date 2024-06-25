package hr.algebra.thewineboutique.controller.mvc;

import hr.algebra.thewineboutique.model.Wine;
import hr.algebra.thewineboutique.model.WineCategoryEnum;
import hr.algebra.thewineboutique.publisher.CustomSpringEventPublisher;
import hr.algebra.thewineboutique.service.CartService;
import hr.algebra.thewineboutique.service.WineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller

@RequestMapping("/TheWineBoutique")
public class WineStoreController extends BaseController {

    private final WineService wineService;
    private final CustomSpringEventPublisher publisher;


    public WineStoreController(CartService cartService, WineService wineService, CustomSpringEventPublisher publisher) {
        super(cartService);
        this.wineService = wineService;
        this.publisher = publisher;
    }

    @GetMapping("/wineStore")
    public String getWineStorePage(Model model) {
        model.addAttribute("wineCategoryList", WineCategoryEnum.values());
        return "wineStore";
    }

    @GetMapping("/category/{category}")
    public String getWinesByCategory(@PathVariable("category") WineCategoryEnum category, Model model) {
        List<Wine> wines = wineService.filterByCategory(category);
        model.addAttribute("wines", wines);
        model.addAttribute("category", category);
        return "wineCategory";
    }

    @GetMapping("/all")
    public String getAllWines(Model model) {
        List<Wine> wines = wineService.findAll();
        model.addAttribute("wines", wines);
        return "allcategorywines";
    }
}
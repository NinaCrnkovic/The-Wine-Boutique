package hr.algebra.thewineboutique.controller.mvc;

import hr.algebra.thewineboutique.model.Wine;
import hr.algebra.thewineboutique.model.WineCategoryEnum;
import hr.algebra.thewineboutique.publisher.CustomSpringEventPublisher;
import hr.algebra.thewineboutique.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import hr.algebra.thewineboutique.dto.WineDTO;
import hr.algebra.thewineboutique.model.WineSearchForm;
import hr.algebra.thewineboutique.service.WineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/TheWineBoutique")

public class WineSearchController extends BaseController{


    private WineService wineService;
    private CustomSpringEventPublisher publisher;

    public WineSearchController(CartService cartService, WineService wineService, CustomSpringEventPublisher publisher) {
        super(cartService);
        this.wineService = wineService;
        this.publisher = publisher;
    }

    @GetMapping("/search")
    public String getSearchPage(Model model, HttpServletRequest request) {

        request.getSession(true);
        publisher.publishCustomEvent("Filtering started!");



        model.addAttribute("wineCategoryList", WineCategoryEnum.values());
        model.addAttribute("wineSearchForm", new WineSearchForm());
        return "wineSearch";
    }

    @PostMapping("/search")
    public String searchWines(WineSearchForm wineSearchForm, Model model) {
        List<Wine> searchResults = wineService.filter(wineSearchForm);

        model.addAttribute("searchResults", searchResults);
        model.addAttribute("wineCategoryList", WineCategoryEnum.values());
        return "wineSearch";
    }


    @GetMapping("/ajaxSearch")
    @ResponseBody
    public List<Wine> ajaxSearch(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String description,
                                 @RequestParam(required = false) WineCategoryEnum category,
                                 @RequestParam(required = false) String type,
                                 @RequestParam(required = false) String vintage,
                                 @RequestParam(required = false) String country,
                                 @RequestParam(required = false) String winery,
                                 @RequestParam(required = false) BigDecimal priceFrom,
                                 @RequestParam(required = false) BigDecimal priceTo) {
        WineSearchForm searchForm = new WineSearchForm();
        searchForm.setName(name);
        searchForm.setDescription(description);
        searchForm.setCategory(category);
        searchForm.setType(type);
        searchForm.setVintage(vintage);
        searchForm.setCountry(country);
        searchForm.setWinery(winery);
        searchForm.setPriceFrom(priceFrom);
        searchForm.setPriceTo(priceTo);
        return wineService.filter(searchForm);
    }

}
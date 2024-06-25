package hr.algebra.thewineboutique.controller.rest;

import hr.algebra.thewineboutique.model.Wine;
import hr.algebra.thewineboutique.model.WineCategoryEnum;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest")
@AllArgsConstructor
public class wineRestController {


    private static List<Wine> wineList;

    static{
        wineList = new ArrayList<>();
        wineList.add(new Wine(1, "Wine1", "Description1", WineCategoryEnum.ROSE, "Type1", "Vintage1", "Country1", "Winery1", new BigDecimal(150), 1, "ImageUrl1"));
        wineList.add(new Wine(2, "Wine2", "Description2",  WineCategoryEnum.ROSE, "Type2", "Vintage2", "Country2", "Winery2", new BigDecimal(150), 2, "ImageUrl2"));
        wineList.add(new Wine(3, "Wine3", "Description3",  WineCategoryEnum.ROSE, "Type3", "Vintage3", "Country3", "Winery3", new BigDecimal(150), 3, "ImageUrl3"));
    }

    @GetMapping("/wines")
    public List<Wine> getWineList() {return wineList;}


    @GetMapping("/wine/{id}")
    public Wine getWineById(@PathVariable Integer id) {

        return wineList.stream()
                .filter(wine -> wine.getId().compareTo(id) == 0)
                        .toList().getFirst();

    }

    @PostMapping("/wine")
    public void createNewWine(@RequestBody Wine wine) {
        wineList.add(wine);
    }

}

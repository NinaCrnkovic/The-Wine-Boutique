package hr.algebra.thewineboutique.service;

import hr.algebra.thewineboutique.dto.WineDTO;
import hr.algebra.thewineboutique.model.Wine;
import hr.algebra.thewineboutique.model.WineCategoryEnum;
import hr.algebra.thewineboutique.model.WineSearchForm;
import hr.algebra.thewineboutique.repository.WineRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WineServiceImpl implements WineService {

    private final WineRepository wineRepository;

    @Override
    public Wine save(Wine wine) {
        return wineRepository.save(wine);
    }

    @Override
    public Wine update(Wine wine) {
        return wineRepository.update(wine);
    }

    @Override
    public void delete(int id) {
        wineRepository.delete(id);
    }

    @Override
    public Wine findById(int id) {
        return wineRepository.findById(id);
    }

    @Override
    public List<Wine> findAll() {
        return wineRepository.findAll();
    }

    @Override
    public List<Wine> filter(WineSearchForm searchForm) {
        return wineRepository.filter(searchForm);
    }

    @Override
    public List<Wine> filterByCategory(WineCategoryEnum category) {
        return wineRepository.findAll().stream()
                .filter(wine -> wine.getCategory() == category)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Wine> findAll(Pageable pageable) {
        return wineRepository.findAll(pageable);
    }

}

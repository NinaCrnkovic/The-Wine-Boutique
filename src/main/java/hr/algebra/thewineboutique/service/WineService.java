package hr.algebra.thewineboutique.service;

import hr.algebra.thewineboutique.dto.WineDTO;
import hr.algebra.thewineboutique.model.Wine;
import hr.algebra.thewineboutique.model.WineCategoryEnum;
import hr.algebra.thewineboutique.model.WineSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface WineService {
    Wine save(Wine wine);
    Wine update(Wine wine);
    void delete(int id);
    Wine findById(int id);
    List<Wine> findAll();
    List<Wine> filter(WineSearchForm searchForm);

    List<Wine> filterByCategory(WineCategoryEnum category);

    Page<Wine> findAll(Pageable pageable);
}

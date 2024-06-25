package hr.algebra.thewineboutique.repository;

import hr.algebra.thewineboutique.model.Wine;
import hr.algebra.thewineboutique.model.WineSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WineRepository {
    Wine save(Wine wine);
    Wine update(Wine wine);
    void delete(int id);
    Wine findById(int id);
    List<Wine> findAll();

    Page<Wine> findAll(Pageable pageable);

    List<Wine> filter(WineSearchForm searchForm);
}

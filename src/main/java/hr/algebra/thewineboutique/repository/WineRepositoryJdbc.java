package hr.algebra.thewineboutique.repository;

import hr.algebra.thewineboutique.model.Wine;
import hr.algebra.thewineboutique.model.WineCategoryEnum;
import hr.algebra.thewineboutique.model.WineSearchForm;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;


import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//@Primary
@Repository


public class WineRepositoryJdbc implements WineRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public WineRepositoryJdbc(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource);
        simpleJdbcInsert.withTableName("WINE")
                .usingGeneratedKeyColumns("ID");
    }


    public Wine save(Wine wine) {
        String sql = "INSERT INTO wine (name, description, category, type, vintage, country, winery, price, quantity, imageUrl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, wine.getName(), wine.getDescription(), wine.getCategory().toString(), wine.getType(), wine.getVintage(), wine.getCountry(), wine.getWinery(), wine.getPrice(), wine.getQuantity(), wine.getImageUrl());

        return wine;
    }
    @Override
    public Wine update(Wine wine) {
        String sql = "UPDATE wine SET name = ?, description = ?, category = ?, type = ?, vintage = ?, country = ?, winery = ?, price = ?, quantity = ?, imageUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql, wine.getName(), wine.getDescription(), wine.getCategory().toString(), wine.getType(), wine.getVintage(), wine.getCountry(), wine.getWinery(), wine.getPrice(), wine.getQuantity(), wine.getImageUrl(), wine.getId());
        return wine;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM wine WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Wine findById(int id) {
        String sql = "SELECT * FROM wine WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new WineRowMapper(), id);
    }

    @Override
    public List<Wine> findAll() {
        String sql = "SELECT * FROM wine";
        return jdbcTemplate.query(sql, new WineRowMapper());
    }

    @Override
    public Page<Wine> findAll(Pageable pageable) {
        String sql = "SELECT * FROM wine LIMIT ? OFFSET ?";
        int totalRows = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM wine", Integer.class);
        List<Wine> wines = jdbcTemplate.query(sql, new WineRowMapper(), pageable.getPageSize(), pageable.getOffset());
        return new PageImpl<>(wines, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), totalRows);
    }

    @Override
    public List<Wine> filter(WineSearchForm searchForm) {
        StringBuilder sql = new StringBuilder("SELECT * FROM wine WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (searchForm.getName() != null && !searchForm.getName().isEmpty()) {
            sql.append(" AND LOWER(name) LIKE ?");
            params.add("%" + searchForm.getName().toLowerCase() + "%");
        }
        if (searchForm.getDescription() != null && !searchForm.getDescription().isEmpty()) {
            sql.append(" AND LOWER(description) LIKE ?");
            params.add("%" + searchForm.getDescription().toLowerCase() + "%");
        }
        if (searchForm.getCategory() != null) {
            sql.append(" AND category = ?");
            params.add(searchForm.getCategory().toString());
        }
        if (searchForm.getType() != null && !searchForm.getType().isEmpty()) {
            sql.append(" AND LOWER(type) LIKE ?");
            params.add("%" + searchForm.getType().toLowerCase() + "%");
        }
        if (searchForm.getVintage() != null && !searchForm.getVintage().isEmpty()) {
            sql.append(" AND LOWER(vintage) LIKE ?");
            params.add("%" + searchForm.getVintage().toLowerCase() + "%");
        }
        if (searchForm.getCountry() != null && !searchForm.getCountry().isEmpty()) {
            sql.append(" AND LOWER(country) LIKE ?");
            params.add("%" + searchForm.getCountry().toLowerCase() + "%");
        }
        if (searchForm.getWinery() != null && !searchForm.getWinery().isEmpty()) {
            sql.append(" AND LOWER(winery) LIKE ?");
            params.add("%" + searchForm.getWinery().toLowerCase() + "%");
        }
        if (searchForm.getPriceFrom() != null) {
            sql.append(" AND price >= ?");
            params.add(searchForm.getPriceFrom());
        }
        if (searchForm.getPriceTo() != null) {
            sql.append(" AND price <= ?");
            params.add(searchForm.getPriceTo());
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new WineRowMapper());
    }
    private static class WineRowMapper implements RowMapper<Wine> {
        @Override
        public Wine mapRow(ResultSet rs, int rowNum) throws SQLException {
            Wine wine = new Wine();
            wine.setId(rs.getInt("id"));
            wine.setName(rs.getString("name"));
            wine.setDescription(rs.getString("description"));
            wine.setCategory(WineCategoryEnum.valueOf(rs.getString("category")));
            wine.setType(rs.getString("type"));
            wine.setVintage(rs.getString("vintage"));
            wine.setCountry(rs.getString("country"));
            wine.setWinery(rs.getString("winery"));
            wine.setPrice(rs.getBigDecimal("price"));
            wine.setQuantity(rs.getInt("quantity"));
            wine.setImageUrl(rs.getString("imageUrl"));
            return wine;
        }


    }
}

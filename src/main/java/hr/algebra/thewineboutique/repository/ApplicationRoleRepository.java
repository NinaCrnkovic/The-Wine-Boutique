package hr.algebra.thewineboutique.repository;


import hr.algebra.thewineboutique.model.ApplicationRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRoleRepository extends JpaRepository<ApplicationRole, Long> {
    ApplicationRole findByName(String name);
}

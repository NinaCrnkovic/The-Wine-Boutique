package hr.algebra.thewineboutique.repository;

import hr.algebra.thewineboutique.model.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {
}

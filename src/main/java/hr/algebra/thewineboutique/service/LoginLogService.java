package hr.algebra.thewineboutique.service;

import hr.algebra.thewineboutique.model.LoginLog;
import hr.algebra.thewineboutique.repository.LoginLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LoginLogService {
    private LoginLogRepository loginLogRepository;

    public List<LoginLog> getAllLoginLogs() {
        return loginLogRepository.findAll();
    }
}

package hr.algebra.thewineboutique.listener;

import hr.algebra.thewineboutique.event.CustomSpringEvent;

import hr.algebra.thewineboutique.model.LoginLog;
import hr.algebra.thewineboutique.repository.LoginLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class CustomSpringEventListener implements ApplicationListener<AuthenticationSuccessEvent> {


    private LoginLogRepository loginLogRepository;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        String ipAddress = ((WebAuthenticationDetails) event.getAuthentication().getDetails()).getRemoteAddress();

        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setLoginTime(LocalDateTime.now());
        loginLog.setIpAddress(ipAddress);

        loginLogRepository.save(loginLog);
    }
}

package hr.algebra.thewineboutique.event;

import org.springframework.context.ApplicationEvent;

public class LoginFailureEvent extends ApplicationEvent {
    private String username;

    public LoginFailureEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
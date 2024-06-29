package hr.algebra.thewineboutique.service;

import hr.algebra.thewineboutique.dto.UserRegistrationDto;
import hr.algebra.thewineboutique.model.ApplicationUser;
import hr.algebra.thewineboutique.model.ApplicationRole;
import hr.algebra.thewineboutique.model.CartItem;
import hr.algebra.thewineboutique.repository.ApplicationRoleRepository;
import hr.algebra.thewineboutique.repository.SpringDataJpaApplicationUserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor

public class UserDetailsServiceImpl implements UserDetailsService {

    private final SpringDataJpaApplicationUserRepository userRepository;
    private final ApplicationRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;




    public void save(UserRegistrationDto registrationDto) {
        ApplicationUser user = new ApplicationUser();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setEmail(registrationDto.getEmail());
        user.setAddress(registrationDto.getAddress());
        user.setCountry(registrationDto.getCountry());
        user.setCity(registrationDto.getCity());

        // Check if the role exists in the database
        ApplicationRole userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = new ApplicationRole("ROLE_USER");
            roleRepository.save(userRole);
        }

        user.setRoles(Collections.singletonList(userRole));
        userRepository.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<String> rolesString = user.getRoles().stream()
                .map(ApplicationRole::getName)
                .toList();

        String[] rolesStringArray = rolesString.toArray(new String[0]);

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(rolesStringArray)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public ApplicationUser authenticate(String username, String password) {
        ApplicationUser user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public ApplicationUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return userRepository.findByUsername(currentUsername);
    }




}
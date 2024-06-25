package hr.estudent.webshop.estudentwebshop.service;

import hr.estudent.webshop.estudentwebshop.models.Roles;
import hr.estudent.webshop.estudentwebshop.models.User;
import hr.estudent.webshop.estudentwebshop.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        String[] roles = user.getRoles().stream()
                .map(Roles::getName)
                .toArray(String[]::new);


        userLogService.logActivity(username, request.getRemoteAddr());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getCurrentUser() {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public String[] findUsersRoles() {
        User user = getCurrentUser();

        if(user == null) {
            return new String[0];
        }

        return user.getRoles().stream()
                .map(Roles::getName)
                .toArray(String[]::new);
    }
}

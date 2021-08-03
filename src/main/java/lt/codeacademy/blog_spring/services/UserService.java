package lt.codeacademy.blog_spring.services;

import lt.codeacademy.blog_spring.dtos.UserRegistrationDTO;
import lt.codeacademy.blog_spring.entities.User;
import lt.codeacademy.blog_spring.exceptions.RoleNotFoundException;
import lt.codeacademy.blog_spring.exceptions.UserNotFoundException;
import lt.codeacademy.blog_spring.repositories.RoleRepository;
import lt.codeacademy.blog_spring.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username).orElse(null);
    }

    public User createUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User(userRegistrationDTO);

        if (userRepository.getUserByUsername(user.getUsername()).isPresent()) {
            return null;
        }

        user.setRoles(Set.of(roleRepository.getRoleByName("USER").orElseThrow(() -> new RoleNotFoundException("USER"))));

        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }
}

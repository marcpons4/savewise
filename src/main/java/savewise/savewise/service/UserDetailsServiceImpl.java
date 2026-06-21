package savewise.savewise.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import savewise.savewise.entity.User;
import savewise.savewise.repository.UserRepository;

@Service
public class UserDetailsServiceImpl
        implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }

        @Override
        public UserDetails loadUserByUsername(String username)
                throws UsernameNotFoundException {

        System.out.println("LOGIN => " + username);

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
        }
}
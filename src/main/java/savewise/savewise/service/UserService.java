package savewise.savewise.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import savewise.savewise.dto.RegisterDto;
import savewise.savewise.entity.User;
import savewise.savewise.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder encoder) {

        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void register(RegisterDto dto) {

        if(userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(
                encoder.encode(dto.getPassword()));

        userRepository.save(user);
    }
}
package workout.gym.domain.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.gym.domain.user.User;
import workout.gym.domain.user.UserRepository;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class LoginService {

    private final UserRepository userRepository;

    public User login(String username, String password) {
        return userRepository.findByUsername(username).filter(u -> u.getPassword().equals(password)).orElse(null);
    }
}

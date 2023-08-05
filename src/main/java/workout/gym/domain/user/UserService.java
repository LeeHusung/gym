package workout.gym.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    public User findById(Long id) {
        User user = userRepository.findById(id);
        return user;
    }
}

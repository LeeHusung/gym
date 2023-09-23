package workout.gym.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.gym.common.exception.DataNotFoundException;
import workout.gym.domain.entity.Address;
import workout.gym.domain.entity.User;
import workout.gym.domain.entity.UserRole;
import workout.gym.web.login.JoinForm;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(JoinForm joinForm) {
        Address address = new Address(joinForm.getCity(), joinForm.getStreet(), joinForm.getZipcode());
        User user = User.builder()
                .userRole(joinForm.getUserRole())
                .username(joinForm.getUsername())
                .password(passwordEncoder.encode(joinForm.getPassword1()))
                .email(joinForm.getEmail())
                .realname(joinForm.getRealname())
                .address(address)
                .nickname(joinForm.getNickname())
                .phone(joinForm.getPhone())
                .build();
        userRepository.save(user);
        return user;
    }

    public User findById(Long id) {
        User user = userRepository.findById(id);
        return user;
    }

    public User getUser(String username) {
        Optional<User> findUser = userRepository.findByUsername(username);
        if (findUser.isPresent()) {
            return findUser.get();
        } else {
            throw new DataNotFoundException("findUser Not Found");
        }
    }
}

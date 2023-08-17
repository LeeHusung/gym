package workout.gym.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workout.gym.domain.entity.Address;
import workout.gym.domain.entity.User;
import workout.gym.domain.entity.UserRole;
import workout.gym.web.login.JoinForm;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(UserRole userRole, Address address, String username, String password, String email, String realname, String nickname, String phone) {
        User user = new User();
//        Address address = new Address(joinForm.getCity(), joinForm.getStreet(), joinForm.getZipcode());
        user.setUserRole(userRole);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRealname(realname);
        user.setAddress(address);
        user.setNickname(nickname);
        user.setPhone(phone);
        userRepository.save(user);
        return user;
    }

    public User findById(Long id) {
        User user = userRepository.findById(id);
        return user;
    }
}

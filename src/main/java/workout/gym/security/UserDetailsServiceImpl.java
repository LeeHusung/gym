//package workout.gym.security;
//
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import workout.gym.domain.User;
//import workout.gym.repository.UserRepository;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public UserDetailsServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = new User();
//        return new org.springframework.security.core.userdetails.User(
//                user.getUserName(),
//                user.getUserPw(),
//                AuthorityUtils.createAuthorityList("USER") // 여기에 해당 사용자의 권한 정보를 추가할 수 있습니다.
//        );
//    }
//}

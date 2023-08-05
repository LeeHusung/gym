//package workout.config.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import workout.gym.domain.User;
//import workout.gym.repository.UserRepository;
//import workout.gym.service.UserService;
//
//@Service
//@RequiredArgsConstructor
//public class ServiceUserDetailService implements UserDetailsService {
//
//    private final UserService userService;
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        ServiceUserDetail serviceUserDetail = new ServiceUserDetail();
//        serviceUserDetail.setId(user.getId());
//        serviceUserDetail.setUsername(user.getUsername());
//        serviceUserDetail.setPassword(new SimplePasswordEncoder().encode(user.getPassword()));
//        // 여기서 ServiceUserDetail에 권한 정보를 추가할 수 있으면 추가해줍니다.
//        // 예를 들면 serviceUserDetail.setAuthorities(...);
//        return serviceUserDetail;
//    }
//
//}
package workout.gym.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import workout.gym.domain.entity.UserRole;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .antMatchers("/items/new/**").hasAuthority(UserRole.ADMIN.getValue()) // 예: "/admin/**" 패턴은 ADMIN 권한이 필요
                        .antMatchers("/myPage/**", "/community/new/**").hasAnyAuthority(UserRole.USER.getValue(), UserRole.ADMIN.getValue())   // 예: "/user/**" 패턴은 USER, ADMIN 권한이 필요
                        .anyRequest().permitAll())                 // 나머지 URL은 모두 접근 허용
//                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                .csrf((csrf) -> csrf //CSRF(Cross-Site Request Forgery) 공격을 방지하기 위해 사용되는 설정
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/**")))
                .formLogin((formLogin) -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/"))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))
        ;
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    // AuthenticationManager는 스프링 시큐리티의 인증을 담당한다.
    // AuthenticationManager는 사용자 인증시 앞에서 작성한 UserSecurityService와 PasswordEncoder를 사용한다.

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
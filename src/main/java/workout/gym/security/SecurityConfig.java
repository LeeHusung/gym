//package workout.gym.security;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//
//@EnableWebSecurity
//@RequiredArgsConstructor
//@Slf4j
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final PasswordEncoder passwordEncoder;
//    private final UserDetailsServiceImpl userDetailsService;
//    //사용자의 패스워드를 평문으로 저장하는 것을 방지하기 위해 사용
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }
//
//    // 이 메서드는 Spring Security가 사용자를 인증하는 방법을 설정하는데 사용
//        // 이 예제에서는 InMemoryAuthentication을 사용하여 사용자 정보를 메모리에 저장
//        // 사용자 이름은 "user"이고 패스워드는 "password"이며, 이 사용자에게 "USER" 역할이 할당
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/user").authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login") // 커스텀 로그인 페이지 지정
//                .defaultSuccessUrl("/main", true) // 로그인 성공 시 이동할 페이지
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")); // 인증 실패 시 이동할 페이지
//        // Spring Security가 HTTP 요청과 접근 제어를 처리하는 방법을 설정하는데 사용
//        //루트 경로("/")로의 요청은 모든 사용자에게 허용
//        //"/user" 경로로의 요청은 인증(로그인)을 필요
//        //커스텀 로그인 페이지를 "/login"으로 지정
//        //로그인 성공 후 기본적으로 "/main" 페이지로 리디렉션
//        //LoginUrlAuthenticationEntryPoint는 인증 실패를 처리하고 사용자를 "/login" 페이지로 리디렉션하는데 사용
//
//        // 로그아웃 처리 설정
//        http.logout()
//                .logoutUrl("/logout") // 로그아웃 URL
//                .logoutSuccessUrl("/index") // 로그아웃 성공 시 이동할 페이지
//                .invalidateHttpSession(true) //로그아웃 후 사용자의 HTTP 세션을 무효화하여 세션과 관련된 데이터를 지움
//                .deleteCookies("JSESSIONID"); //로그아웃 후 "JSESSIONID" 쿠키를 삭제하여 세션과 관련된 쿠키를 제거
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//    // 이 메서드는 AuthenticationManager를 빈으로 노출시키는데 사용. 이는 로그인 프로세스에서 AuthenticationManager를 사용하기 위해 필요
//}

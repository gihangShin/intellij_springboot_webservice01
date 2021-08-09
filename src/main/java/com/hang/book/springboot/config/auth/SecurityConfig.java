package com.hang.book.springboot.config.auth;

import com.hang.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션 disable
                .and()
                    .authorizeRequests()    // URL 별 권한 관리를 설정하는 옵션의 시작점. (이게 선언되어야 antMatchers 옵션 사용 가능)
                    .antMatchers("/", "/css/*", "/images/**", "/js/**", "/h2-console/**").permitAll()   // 지정된 URL 들은 permitAll 옵션을 통해 전체 열람 권한
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())    // antMatchers 권한 관리 대상을 지정하는 옵션 hasRole 옵션을 통해 USER 권한을 가진 사람만 열람 권한
                    .anyRequest().authenticated()   // 설정되지 않은 나머지 URL 의미 authenticated()을 추가하여 인증된 사용자들에게만 허용.
                .and()
                    .logout()
                        .logoutSuccessUrl("/")  // 로그아웃 기능에 대한 여러 설정의 진입점. -> 로그아웃 성공시 "/" 주소로 이동
                .and()
                    .oauth2Login()  // OAuth2 로그인 기능에 대한 여러 설정의 진입점.
                        .userInfoEndpoint() // 로그인 성공 이후 사용자 정보를 가져올 때의 설정 담당.
                            .userService(customOAuth2UserService);  // 로그인 성공시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
//        super.configure(http);
    }
}

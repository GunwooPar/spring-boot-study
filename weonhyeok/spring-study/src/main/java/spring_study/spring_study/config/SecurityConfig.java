package spring_study.spring_study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration  //컴포넌트 스캔 방식이 아닌 Bean 등록  post 요청 form에 자동 CSRF 토큰 포함
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) ->authorizeHttpRequests  //인증되지 않은 모든 페이지 요청 허락
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated())
                .csrf((csrf) -> csrf
                    .ignoringRequestMatchers("/h2-console/**")) //H2-console은 csrf 검증 하지 않게 설정
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN
                        )));
        return http.build();
    }
}

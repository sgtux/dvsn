package br.com.dvsn.security;

import br.com.dvsn.auth.filters.CookieBase64AuthenticationFilter;
import br.com.dvsn.auth.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CookieBase64AuthenticationFilter cookieBase64AuthenticationFilter;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**", "/", "/index.html", "/static/**", "/fontawesome/**", "/image/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .headers()
                .xssProtection().disable()
                .cacheControl().disable()
                .and()
                .cors().disable()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(cookieBase64AuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, CookieBase64AuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        return http.build();
    }
}

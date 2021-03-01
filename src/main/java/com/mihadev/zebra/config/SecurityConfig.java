package com.mihadev.zebra.config;

import com.mihadev.zebra.security.JWTConfigurer;
import com.mihadev.zebra.security.JWTTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTTokenProvider jwtTokenProvider;

    public SecurityConfig(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login","/cache/**", "/clients/**").permitAll()
                .antMatchers("/salary", "/price").hasAnyRole("ADMIN")
                .antMatchers(
                        "abons/**",
                        "classes/**",
                        "coach-app/**",
                        "coaches/**",
                        "scheduleClass/**",
                        "schedule/**",
                        "students/**",
                        "telegram/**",
                        "users/**"
                ).hasAnyRole("ADMIN", "COACH", "USER")
                .anyRequest().authenticated()
                .and()
                .apply(new JWTConfigurer(jwtTokenProvider));
    }
}

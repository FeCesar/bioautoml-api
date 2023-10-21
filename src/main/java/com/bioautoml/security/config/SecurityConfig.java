package com.bioautoml.security.config;

import com.bioautoml.domain.role.enums.Role;
import com.bioautoml.domain.user.service.UserService;
import com.bioautoml.filters.AuthenticationFilter;
import com.bioautoml.security.services.AuthenticationService;
import com.bioautoml.security.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "*").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api-docs/**").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/auth").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/users").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/users/*").hasAnyAuthority(Role.ADMIN.name(), Role.DEFAULT.name())
                .antMatchers(HttpMethod.GET, "/users/*/*").hasAnyAuthority(Role.ADMIN.name(), Role.DEFAULT.name())
                .antMatchers(HttpMethod.GET, "/processes").hasAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/processes/*").hasAnyAuthority(Role.ADMIN.name(), Role.DEFAULT.name())
                .antMatchers(HttpMethod.PUT, "/processes").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/actuator/*").hasAnyAuthority(Role.ADMIN.name())
                .anyRequest().authenticated()
                .and().cors(Customizer.withDefaults())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AuthenticationFilter(jwtService, userService), UsernamePasswordAuthenticationFilter.class);
    }

}

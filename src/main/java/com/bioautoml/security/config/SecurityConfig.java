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
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user/").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/").permitAll()
                .antMatchers(HttpMethod.GET, "/user/").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/user/*").hasAnyAuthority(Role.ADMIN.name(), Role.DEFAULT.name())
                .antMatchers(HttpMethod.POST, "/role/").hasAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/role/grant/").hasAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/role/").hasAuthority(Role.ADMIN.name())
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AuthenticationFilter(jwtService, userService), UsernamePasswordAuthenticationFilter.class);
    }

}

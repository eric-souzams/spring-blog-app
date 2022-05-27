package io.blog.springblogapp.security;

import io.blog.springblogapp.security.filters.AuthenticationFilter;
import io.blog.springblogapp.security.filters.AuthorizationFilter;
import io.blog.springblogapp.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, SecurityConstants.LOGIN_URL).permitAll()
                    .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
                    .antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL).permitAll()
                    .antMatchers(HttpMethod.POST, SecurityConstants.RESET_PASSWORD_URL).permitAll()
                    .antMatchers(HttpMethod.POST, SecurityConstants.RESET_PASSWORD_UPDATE_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilter(getAuthenticationFilter())
                    .addFilterBefore(new AuthorizationFilter(authenticationManager(), getJwtService()), UsernamePasswordAuthenticationFilter.class);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), getJwtService());
        authenticationFilter.setFilterProcessesUrl(SecurityConstants.LOGIN_URL);

        return authenticationFilter;
    }

    public JwtService getJwtService() {
        return jwtService;
    }
}

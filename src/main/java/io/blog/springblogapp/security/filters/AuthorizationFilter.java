package io.blog.springblogapp.security.filters;

import io.blog.springblogapp.security.SecurityConstants;
import io.blog.springblogapp.service.JwtService;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtService jwtService;

    public AuthorizationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String requestHeader = request.getHeader(SecurityConstants.HEADER_STRING);

        if (requestHeader == null) {
            chain.doFilter(request, response);
            return;
        }

        if (!requestHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(requestHeader);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header) {
        String token = header.replace(SecurityConstants.TOKEN_PREFIX, "");

        String user = jwtService.getUsername(token);

        if (!jwtService.isValidToken(token)) {
            return null;
        }

        if (user == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }
}

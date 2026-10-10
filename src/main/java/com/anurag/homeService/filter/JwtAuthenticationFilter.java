package com.anurag.homeService.filter;

import com.anurag.homeService.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

      @Autowired
      private JwtService jwtService;

      @Override
      protected void doFilterInternal(HttpServletRequest request,
                                      HttpServletResponse response,
                                      FilterChain filterChain) throws ServletException, IOException {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                  String token = authHeader.substring(7);

                  if (jwtService.isValid(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                        String email = jwtService.extractEmail(token);
                        String role = jwtService.extractRole(token);

                        if (email != null && role != null) {
                              SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                              UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                          email,
                                          null,
                                          Collections.singletonList(authority)
                              );
                              authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                              SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                  }
            }

            filterChain.doFilter(request, response);
      }
}

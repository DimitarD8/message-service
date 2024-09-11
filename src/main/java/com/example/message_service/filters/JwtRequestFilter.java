package com.example.message_service.filters;

import com.example.message_service.service.KafkaConsumerService;
import com.example.message_service.utils.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final KafkaConsumerService kafkaConsumerService;

    public JwtRequestFilter(KafkaConsumerService kafkaConsumerService) {
        this.kafkaConsumerService = kafkaConsumerService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String jwtToken = null;
        String username = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {



                String signingKey = kafkaConsumerService.getSecretKey();
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(signingKey.getBytes(StandardCharsets.UTF_8)) // Ensure consistent encoding
                        .build()
                        .parseClaimsJws(jwtToken)
                        .getBody();
                username = claims.getSubject();
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (SignatureException e) {
                System.out.println("JWT Token has expired or is incorrect");
                e.printStackTrace();
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = new CustomUserDetails(username);
            if (validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
    }

    private boolean validateToken(String token, UserDetails userDetails) {
        return true;
    }
}

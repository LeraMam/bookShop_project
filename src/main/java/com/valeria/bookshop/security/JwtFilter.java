package com.valeria.bookshop.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.valeria.bookshop.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final UserService userService;

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        return Arrays.stream(request.getCookies() != null ? request.getCookies() : new Cookie[]{})
                .filter(cookie -> cookie.getName().equals("access_token"))
                .map(cookie -> cookie.getValue())
                .filter(value -> StringUtils.hasLength(value))
                .findFirst();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final Optional<String> optionalToken = getTokenFromRequest(request);
        if (optionalToken.isPresent()) {
            try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(optionalToken.get());
                final JwtAuthentication jwtInfoToken = new JwtAuthentication();
                jwtInfoToken.setAuthenticated(true);
                jwtInfoToken.setRoles(userService.getRolesForUserByIdentityId(decodedToken.getUid()));
                jwtInfoToken.setEmail(decodedToken.getEmail());
                jwtInfoToken.setFirstName(decodedToken.getName());
                jwtInfoToken.setFirebaseIdentityId(decodedToken.getUid());
                SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
            } catch (FirebaseAuthException e) {

            }
        }
        filterChain.doFilter(request, response);
    }
}

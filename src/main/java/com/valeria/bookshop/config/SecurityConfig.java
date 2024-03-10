package com.valeria.bookshop.config;

import com.valeria.bookshop.security.JwtFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.util.WebUtils;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig implements WebSecurityConfigurer<WebSecurity> {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        /*http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                .requestMatchers(HttpMethod.GET, "/api/category").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/brand").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/group").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/meta/years").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/item").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/meta/price").permitAll()
                .requestMatchers(HttpMethod.GET, "/catalog").permitAll()
                .anyRequest().authenticated()
        );*/
/*
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .anyRequest().permitAll()
        );*/
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);


        http.addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        /*
        http.oauth2ResourceServer((oauth2) -> {
            oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)).bearerTokenResolver(this::tokenExtractor);
        });*/
                /*.addFilterAfter((servletRequest, servletResponse, filterChain) ->
                                System.out.println(SecurityContextHolder.getContext().getAuthentication().getName()),
                        SecurityContextHolderAwareRequestFilter.class)*/
        return http.build();
    }
/*

    @Bean
    WebSecurity webSecurity(WebSecurity webSecurity) {
        webSecurity.ignoring().requestMatchers(HttpMethod.GET, "/api/category");

    }
*/

    public String tokenExtractor(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, "access_token");
        if (cookie != null)
            return cookie.getValue();
        return "";
    }
/*

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(UserRepository userRepository) {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
                    var role = userRepository.findByFirebaseIdentityId(jwt.getSubject())
                            .map(userEntity -> new SimpleGrantedAuthority(userEntity.getRole().name()))
                            .orElse(new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.name()));
                    return List.of(role);
                }
        );
        return converter;
    }
*/

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    @Override
    public void init(WebSecurity builder) throws Exception {

    }

    @Override
    public void configure(WebSecurity builder) throws Exception {
/*        builder.ignoring()
                //.requestMatchers(HttpMethod.GET, "/catalog")
                .requestMatchers(HttpMethod.GET, "/js/**")
                .requestMatchers(HttpMethod.GET, "/css/**")
                .requestMatchers(HttpMethod.GET, "/images/**")
                .requestMatchers(HttpMethod.GET, "/fonts/**")
                .requestMatchers(HttpMethod.GET, "/api/category")
                .requestMatchers(HttpMethod.GET, "/api/brand")
                .requestMatchers(HttpMethod.GET, "/api/group")
                .requestMatchers(HttpMethod.GET, "/api/meta/years")
                .requestMatchers(HttpMethod.GET, "/api/item")
                .requestMatchers(HttpMethod.GET, "/api/meta/price");*/
    }
/*
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }*/
}

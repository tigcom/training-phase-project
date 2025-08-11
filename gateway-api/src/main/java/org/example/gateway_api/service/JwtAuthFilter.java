//package org.example.gateway_api.service;
//
//import java.io.IOException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Service;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Service
//public class JwtAuthFilter extends OncePerRequestFilter {
//    @Autowired
//    private JwtService jwtService;
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//        String bearer = "Bearer ";
//        String auth = request.getHeader("Authorization");
//
//        if (auth != null && auth.startsWith(bearer)
//                && SecurityContextHolder.getContext().getAuthentication() == null) {
//            String token = auth.substring(bearer.length());
//            String username = jwtService.getUsername(token);
//            UserDetails user = userDetailsService.loadUserByUsername(username);
//            if (jwtService.validateToken(token, user)) {
//                var authToken = new UsernamePasswordAuthenticationToken(
//                        user, null, user.getAuthorities()
//                );
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//}

package com.swp391.teamfour.forbadsystem.jwt;

import com.swp391.teamfour.forbadsystem.constants.AuthenticationPath;
import com.swp391.teamfour.forbadsystem.exception.AuthenticationExceptionHandler;
import com.swp391.teamfour.forbadsystem.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        try {
            String token = getTokenFromRequest(request);

            if (AuthenticationPath.requiresAuthentication(request)) {
                if (!StringUtils.hasText(token)) {
                    AuthenticationExceptionHandler.handleJwtError(request, response, "Không có token.");
                    return;
                }

                if (!jwtTokenProvider.validateJwtToken(token)) {
                    AuthenticationExceptionHandler.handleJwtError(request, response, "Token không hợp lệ hoặc đã hết hạn.");
                    return;
                }

                String email = jwtTokenProvider.getUserNameFromJwtToken(token);
                UserDetails userDetails = userService.loadUserByUsername(email);

                if (userDetails == null) {
                    AuthenticationExceptionHandler.handleJwtError(request, response, "Token không hợp lệ.");
                    return;
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Có lỗi xảy ra trong quá trình xác thực: {}", e);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }


}

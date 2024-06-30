package com.swp391.teamfour.forbadsystem.constants;

import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationPath {
    public static boolean requiresAuthentication(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !requestURI.startsWith("/auth/") &&
                !requestURI.startsWith("/forgot-password/") &&
                !requestURI.startsWith("/swagger-ui/") &&
                !requestURI.startsWith("/v3/api-docs/") &&
                !requestURI.startsWith("/court/latest-courts") &&
                !requestURI.startsWith("/court/facilities-of-court/") &&
                !requestURI.startsWith("/yard-schedule/getAllByYardId/") &&
                !requestURI.startsWith("/court/all");
    }
}

package com.swp391.teamfour.forbadsystem.constants;

import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationPath {
    public static boolean requiresAuthentication(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !requestURI.startsWith("/auth/") &&
                !requestURI.startsWith("/forgot-password/") &&
                !requestURI.startsWith("/court/all");
    }
}

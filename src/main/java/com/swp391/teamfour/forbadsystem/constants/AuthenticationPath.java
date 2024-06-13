package com.swp391.teamfour.forbadsystem.constants;

import jakarta.servlet.http.HttpServletRequest;


public class AuthenticationPath {
    public static boolean requiresAuthentication(HttpServletRequest request) {
        return !request.getRequestURI().startsWith("/auth/") && !request.getRequestURI().startsWith("/forgot-password/");
    }
}

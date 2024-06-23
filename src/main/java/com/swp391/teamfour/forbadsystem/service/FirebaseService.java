package com.swp391.teamfour.forbadsystem.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FirebaseService {

    String upload(MultipartFile file) throws IOException;
}

package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.swp391.teamfour.forbadsystem.service.FirebaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseStorageService implements FirebaseService {
    private final Bucket bucket;

    public FirebaseStorageService() {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        this.bucket = storage.get("forbad-43f1e");
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String blobName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        BlobInfo blobInfo = BlobInfo.newBuilder(bucket.getName(), blobName).build();
        bucket.create(blobInfo.getName(), file.getBytes(), file.getContentType());
        return blobInfo.getMediaLink();
    }
}

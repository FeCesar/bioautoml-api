package com.bioautoml.storage;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public interface Storage {

    void createConnection();
    void createFolder(String folderPath, InputStream file, ObjectMetadata data);
    void createFolders(List<MultipartFile> files, UUID processId);
    URL generateFileURL(String processId);

}

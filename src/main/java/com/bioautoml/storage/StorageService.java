package com.bioautoml.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class StorageService {

    @Autowired
    private AmazonCredentials amazonCredentials;

    private AmazonS3 s3client;

    private final String SEPARATOR = "/";

    private final Gson gson = new Gson();
    private final Logger logger = LoggerFactory.getLogger(StorageService.class);

    @PostConstruct
    public void createConnection(){
        AWSCredentials credentials = new BasicAWSCredentials(this.amazonCredentials.getAwsAccessKey(), this.amazonCredentials.getAwsSecretKey());

        this.s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }


    public void createFolder(String folderPath, InputStream file, ObjectMetadata data){
        this.s3client.putObject(this.amazonCredentials.getBucketName(), folderPath, file, data);
        logger.info("folder={} created to storage", folderPath);
    }


    public void createFolders(List<MultipartFile> files, UUID processId){
        files.forEach(file -> {
                    String folderPath = String.valueOf(processId).concat(this.SEPARATOR).concat(Objects.requireNonNull(file.getOriginalFilename()));

                    ObjectMetadata data = new ObjectMetadata();
                    data.setContentType(file.getContentType());
                    data.setContentLength(file.getSize());

                    try {
                        this.createFolder(folderPath, file.getInputStream(), data);
                    } catch (IOException e) {
                        logger.error("file={} was failed. stack={}", file.getOriginalFilename(), e.getMessage());
                    }
                });
    }

}
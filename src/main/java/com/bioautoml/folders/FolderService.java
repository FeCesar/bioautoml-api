package com.bioautoml.folders;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class FolderService {

    @Autowired
    private AmazonCredentials amazonCredentials;

    private AmazonS3 s3client;

    private final String SEPARATOR = "/";

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
    }


    public void createFolders(List<MultipartFile> files, UUID processId){
        files.forEach(file -> {
            String folderPath = String.valueOf(processId).concat(this.SEPARATOR).concat(file.getOriginalFilename());

            ObjectMetadata data = new ObjectMetadata();
            data.setContentType(file.getContentType());
            data.setContentLength(file.getSize());

            try {
                this.createFolder(folderPath, file.getInputStream(), data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}

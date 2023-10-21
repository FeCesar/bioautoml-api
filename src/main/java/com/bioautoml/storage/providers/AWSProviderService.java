package com.bioautoml.storage.providers;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bioautoml.domain.commons.DateTimeHelper;
import com.bioautoml.storage.Storage;
import com.bioautoml.storage.credentials.AmazonCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Instant;
import java.util.*;

@Service
public class AWSProviderService implements Storage {

    @Autowired
    private AmazonCredentials amazonCredentials;

    private AmazonS3 s3client;

    private final String SEPARATOR = "/";

    private final Logger logger = LoggerFactory.getLogger(AWSProviderService.class);

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


    public void createFolders(List<MultipartFile> files, Long processId){
        files.forEach(file -> {
            String folderPath = processId.toString() + this.SEPARATOR +
                Objects.requireNonNull(file.getOriginalFilename()).strip().toLowerCase(Locale.ROOT);

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

    public URL generateFileURL(Long processId) {
        Date expiration = this.generateDefaultExpirationTimeMillis();

        String filePathS3 = processId + "/results.zip";

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
            new GeneratePresignedUrlRequest(amazonCredentials.getBucketName(), filePathS3)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        return this.s3client.generatePresignedUrl(generatePresignedUrlRequest);
    }

    private Date generateDefaultExpirationTimeMillis() {
        Date expiration = new Date();
        long expTimeMillis = Instant.now().toEpochMilli();

        expTimeMillis +=
            DateTimeHelper.MAX_MILLISECONDS *
                DateTimeHelper.MAX_SECONDS *
                DateTimeHelper.MAX_MINUTES *
                DateTimeHelper.MAX_HOURS *
                DateTimeHelper.DAYS_0F_WEEK;

        expiration.setTime(expTimeMillis);

        return expiration;
    }

}

package com.bioautoml.storage.credentials;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Getter
public class AmazonCredentials {

    @Value("${application.aws.access}")
    private String awsAccessKey;

    @Value("${application.aws.secret}")
    private String awsSecretKey;

    @Value("${application.aws.s3.bucket.name}")
    private String bucketName;

}

package com.example.originalspecialmove.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class StorageConfig {

    @Value("${cloudflare.r2.credentials.access-key}")
    private String accessKey;

    @Value("${cloudflare.r2.credentials.secret-key}")
    private String secretKey;

    /**
     * .
     *
     * @return AmazonS3client
     */
    @Bean
    public AmazonS3 fileClient() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new EndpointConfiguration(
                        "https://13292ebd881ea51fc4f5528625ce37b4.r2.cloudflarestorage.com/original-specialmove-battle", "us-east-1"))
                .enablePathStyleAccess()
                .build();
        return s3;
    }

}

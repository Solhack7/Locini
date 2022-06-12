package com.springsecurity.ws.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    BUCKET_NAME("herbalimage");
    private final String bucketName;
}
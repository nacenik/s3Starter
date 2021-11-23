package net.oleksin.s3starter.client;

import lombok.RequiredArgsConstructor;
import net.oleksin.s3starter.properties.S3Properties;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class S3ClientWorker {

    public static final String SEPARATOR = "-";

    private final S3Properties s3Properties;

    public void putObject(InputStream inputStream, String className, Long id) throws IOException {
        S3Client s3 = getS3Client();
        putS3Object(s3, s3Properties.getBucketName(), inputStream, getKeyForS3(s3Properties.getBucketName(), className, id));
        inputStream.close();
        s3.close();
    }

    public void deleteObject(String className, Long id) {
        S3Client s3 = getS3Client();
        deleteObject(s3, s3Properties.getBucketName(), getKeyForS3(s3Properties.getBucketName(), className, id));
        s3.close();
    }

    public List<String> listObjects() {
        S3Client s3 = getS3Client();
        List<String> objects = listObjects(s3, s3Properties.getBucketName());
        s3.close();

        return objects;
    }

    private List<String> listObjects(S3Client s3, String bucketName) {

        try {
            ListObjectsRequest listObjects = ListObjectsRequest
                    .builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsResponse res = s3.listObjects(listObjects);
            return res.contents()
                    .stream()
                    .map(S3Object::key)
                    .collect(Collectors.toList());

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
        return null;
    }

    private void deleteObject(S3Client s3, String bucketName, String key) {
        try {
            DeleteObjectRequest deleteOb = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3.deleteObject(deleteOb);
        } catch (S3Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void putS3Object(S3Client s3, String bucketName, InputStream inputStream, String key) {
        try {
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3.putObject(putOb,
                    RequestBody.fromBytes(inputStream.readAllBytes()));

        } catch (S3Exception | IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private S3Client getS3Client() {
        return S3Client.builder()
                .region(Region.of(s3Properties.getRegion()))
                .build();
    }

    private static String getKeyForS3(String bucketName, String className, Long id) {
        return bucketName + SEPARATOR + className + SEPARATOR + id;
    }

}

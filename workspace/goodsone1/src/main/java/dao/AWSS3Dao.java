package dao;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@PropertySource("classpath:application.properties")
public class AWSS3Dao {
  @Value("${aws.credentials.access-key}")
  private String accessKey;
  @Value("${aws.credentials.secret-key}")
  private String secretKey;
  @Value("${aws.region.static}")
  private String region;
  @Value("${aws.s3.bucket}")
  private String bucket;
  
  
  private final String S3_BUCKET_URL =
      "s3://chamman/estimateImages/";

  public List<String> getS3Urls(String imagesPath) {
    String[] paths = imagesPath.split(",");
    List<String> urls = new ArrayList<>();
    for (String path : paths) {
      urls.add(S3_BUCKET_URL + path);
    }
    return urls;
  }

  public String uploadImagesToS3(List<MultipartFile> images, String phone) throws IOException {
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    String keyPrefix = "estimateImages/" + timestamp + "_" + phone.replace("-", "") + "/";
    StringBuilder imagePaths = new StringBuilder();

    S3Client s3Client = S3Client.builder()
        .region(Region.of(region))
        .credentialsProvider(
            StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
        .build();

    for (MultipartFile image : images) {
        String key = keyPrefix + image.getOriginalFilename();
        try (InputStream inputStream = image.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .acl("public-read")
                .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, image.getSize()));
            imagePaths.append(key).append(",");
        }
    }
    // Remove trailing comma
    if (imagePaths.length() > 0) {
        imagePaths.setLength(imagePaths.length() - 1);
    }

    return imagePaths.toString();
  }
  
  
  
  
}

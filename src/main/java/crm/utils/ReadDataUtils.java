package crm.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ReadDataUtils {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket.name:${S3_BUCKET_NAME:crm-data-storage}}")
    private String bucketName;

    @Value("${app.data.source:${DATA_SOURCE:classpath}}")
    private String dataSource;

    public ReadDataUtils(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * Cloud-native file reading supporting both S3 and classpath resources
     * @param filePath Path to file (S3 key or classpath resource)
     * @param fileExtensions Allowed file extensions for validation
     * @return InputStream for the file content
     */
    public InputStream readFile(String filePath, String... fileExtensions) {
        log.info("Reading file: {} from source: {}", filePath, dataSource);

        // Validate file extension
        if (fileExtensions != null && fileExtensions.length > 0) {
            boolean validExtension = false;
            for (String ext : fileExtensions) {
                if (filePath.toLowerCase().endsWith("." + ext.toLowerCase())) {
                    validExtension = true;
                    break;
                }
            }
            if (!validExtension) {
                throw new IllegalArgumentException("File extension not supported. Allowed: " + String.join(", ", fileExtensions));
            }
        }

        try {
            if ("s3".equalsIgnoreCase(dataSource)) {
                return readFromS3(filePath);
            } else {
                return readFromClasspath(filePath);
            }
        } catch (Exception e) {
            log.error("Error reading file {}: {}", filePath, e.getMessage());
            throw new RuntimeException("Failed to read file: " + filePath, e);
        }
    }

    /**
     * List available files from the configured data source
     * @param prefix Path prefix to filter files
     * @param fileExtensions File extensions to filter
     * @return List of available file paths
     */
    public List<String> listAvailableFiles(String prefix, String... fileExtensions) {
        log.info("Listing files with prefix: {} from source: {}", prefix, dataSource);

        try {
            if ("s3".equalsIgnoreCase(dataSource)) {
                return listFromS3(prefix, fileExtensions);
            } else {
                // For classpath resources, return predefined list or empty
                log.warn("File listing from classpath not fully supported. Consider using S3 for dynamic file listing.");
                return List.of();
            }
        } catch (Exception e) {
            log.error("Error listing files with prefix {}: {}", prefix, e.getMessage());
            throw new RuntimeException("Failed to list files", e);
        }
    }

    private InputStream readFromS3(String s3Key) {
        log.debug("Reading from S3: s3://{}/{}", bucketName, s3Key);
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build();

        return s3Client.getObject(getObjectRequest);
    }

    private InputStream readFromClasspath(String resourcePath) {
        log.debug("Reading from classpath: {}", resourcePath);
        try {
            Resource resource = new ClassPathResource(resourcePath);
            if (!resource.exists()) {
                throw new RuntimeException("Classpath resource not found: " + resourcePath);
            }
            return resource.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read classpath resource: " + resourcePath, e);
        }
    }

    private List<String> listFromS3(String prefix, String... fileExtensions) {
        ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(prefix)
                .build();

        ListObjectsV2Response listResponse = s3Client.listObjectsV2(listRequest);

        return listResponse.contents().stream()
                .map(S3Object::key)
                .filter(key -> {
                    if (fileExtensions == null || fileExtensions.length == 0) {
                        return true;
                    }
                    for (String ext : fileExtensions) {
                        if (key.toLowerCase().endsWith("." + ext.toLowerCase())) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
}

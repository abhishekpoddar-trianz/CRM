package crm.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class ReadDataUtils {

    @Value("${aws.s3.bucket.name:crm-data-files}")
    private String s3BucketName;

    @Value("${app.upload.dir:classpath:data/}")
    private String uploadDirectory;

    /**
     * Cloud-ready method to read file from classpath resources
     * Replaces desktop file chooser with resource-based file reading
     */
    public InputStream readFileFromClasspath(String fileName) throws IOException {
        try {
            Resource resource = new ClassPathResource("data/" + fileName);
            if (resource.exists()) {
                log.info("Reading file from classpath: {}", fileName);
                return resource.getInputStream();
            } else {
                log.warn("File not found in classpath: {}", fileName);
                return null;
            }
        } catch (IOException e) {
            log.error("Error reading file from classpath: {}", fileName, e);
            throw e;
        }
    }

    /**
     * Cloud-ready method to handle uploaded files via HTTP
     * Suitable for cloud environments where file upload is via web interface
     */
    public InputStream readUploadedFile(MultipartFile uploadedFile, String expectedExtension) throws IOException {
        if (uploadedFile == null || uploadedFile.isEmpty()) {
            log.warn("No file uploaded or file is empty");
            return null;
        }

        String fileName = uploadedFile.getOriginalFilename();
        if (fileName == null || !fileName.toLowerCase().endsWith("." + expectedExtension.toLowerCase())) {
            log.warn("Invalid file type. Expected: {}, Got: {}", expectedExtension, fileName);
            return null;
        }

        log.info("Processing uploaded file: {} (size: {} bytes)", fileName, uploadedFile.getSize());
        return uploadedFile.getInputStream();
    }

    /**
     * TODO: Method to read file from AWS S3 bucket
     * This would replace desktop file access in cloud environments
     */
    public InputStream readFileFromS3(String fileName) {
        // TODO: Implement S3 file reading
        // AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();
        // S3Object s3Object = s3Client.getObject(s3BucketName, fileName);
        // return s3Object.getObjectContent();

        log.info("S3 file reading not yet implemented for file: {}", fileName);
        return null;
    }

    /**
     * Validates file extension for cloud-safe file processing
     */
    public boolean isValidFileExtension(String fileName, String... allowedExtensions) {
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }

        String lowerFileName = fileName.toLowerCase();
        for (String ext : allowedExtensions) {
            if (lowerFileName.endsWith("." + ext.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}

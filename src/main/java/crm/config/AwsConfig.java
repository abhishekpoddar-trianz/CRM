package crm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.ssm.SsmClient;

@Configuration
@Slf4j
public class AwsConfig {

    @Value("${aws.s3.region:${AWS_REGION:us-east-1}}")
    private String awsRegion;

    @Bean
    public S3Client s3Client() {
        log.info("Initializing S3Client for region: {}", awsRegion);
        try {
            return S3Client.builder()
                    .region(Region.of(awsRegion))
                    .credentialsProvider(DefaultCredentialsProvider.create())
                    .build();
        } catch (Exception e) {
            log.error("Failed to initialize S3Client: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize S3Client", e);
        }
    }

    @Bean
    public SecretsManagerClient secretsManagerClient() {
        log.info("Initializing SecretsManagerClient for region: {}", awsRegion);
        try {
            return SecretsManagerClient.builder()
                    .region(Region.of(awsRegion))
                    .credentialsProvider(DefaultCredentialsProvider.create())
                    .build();
        } catch (Exception e) {
            log.error("Failed to initialize SecretsManagerClient: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize SecretsManagerClient", e);
        }
    }

    @Bean
    public SsmClient ssmClient() {
        log.info("Initializing SSM Client for region: {}", awsRegion);
        try {
            return SsmClient.builder()
                    .region(Region.of(awsRegion))
                    .credentialsProvider(DefaultCredentialsProvider.create())
                    .build();
        } catch (Exception e) {
            log.error("Failed to initialize SSM Client: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize SSM Client", e);
        }
    }
}
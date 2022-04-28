package gwk.dataGatherer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


@Configuration
public class AwsS3Config {
    private final String accessKey;
    private final String secretKey;
    private final String region;
    private final String bucket;


    public AwsS3Config(@Value("${cloud.aws.credentials.accessKey}") String accessKey,
                       @Value("${cloud.aws.credentials.secretKey}") String secretKey,
                       @Value("${cloud.aws.region.static}") String region,
                       @Value("${bucket.name}") String bucket) {

        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
        this.bucket = bucket;
    }

    public String getBucket() {
        return bucket;
    }

    @Bean
    public AwsCredentials basicAWSCredentials() {
        return AwsBasicCredentials.create(accessKey, secretKey);
    }

    @Bean
    public S3Client s3Client(AwsCredentials awsCredentials) {
        return S3Client.builder().region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials)).build();
    }
}
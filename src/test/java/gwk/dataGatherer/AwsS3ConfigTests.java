package gwk.dataGatherer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class AwsS3ConfigTests {
    @Autowired
    AwsS3Config awsS3config;
    @Value("${bucket.name}") String bucket;

    @Test
    void bucketCheck() {
        assertThat(bucket).isEqualTo(awsS3config.getBucket());
    }
}

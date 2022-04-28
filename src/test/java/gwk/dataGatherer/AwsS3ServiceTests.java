package gwk.dataGatherer;

import io.findify.s3mock.S3Mock;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Import(S3MockConfig.class)
public class AwsS3ServiceTests {

    @Value("${bucket.name}")
    String bucket;
    @Autowired
    AwsS3Service awsS3Service;
    @Autowired
    S3Mock s3Mock;

    @Test
    public void uploadBucketTest() throws IOException {
        String expected = "mock1.png";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", expected,
                "image/png", "test data".getBytes());
        InputStream mockInputStream = mockMultipartFile.getInputStream();
        assertThat(bucket).isEqualTo(awsS3Service.uploadFile(expected, mockInputStream , "test"));
    }
    @After
    public void shutdownMockS3(){
        s3Mock.stop();
    }
}

package gwk.dataGatherer;

import java.io.IOException;
import java.io.InputStream;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

@Service
@RequiredArgsConstructor
public class AwsS3Service {
	private final S3Client client;
	private final AwsS3Config awsS3Config;
	
	public String uploadFile(String fileName, InputStream inputStream, String prefix)
			throws S3Exception, AwsServiceException, SdkClientException, IOException {
		String prefixFileName = prefix + fileName;
		
		PutObjectRequest request = PutObjectRequest.builder()
										.bucket(awsS3Config.getBucket())
										.key(prefixFileName)
										.acl("public-read")
										.build();

		client.putObject(request, 
				RequestBody.fromInputStream(inputStream, inputStream.available()));
		
		S3Waiter waiter = client.waiter();
		HeadObjectRequest waitRequest = HeadObjectRequest.builder()
											.bucket(awsS3Config.getBucket())
											.key(prefixFileName)
											.build();
		
		WaiterResponse<HeadObjectResponse> waitResponse = waiter.waitUntilObjectExists(waitRequest);
		
		waitResponse.matched().response().ifPresent(x -> {
		});
		return request.bucket();
	}
}

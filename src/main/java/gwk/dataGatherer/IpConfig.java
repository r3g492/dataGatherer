package gwk.dataGatherer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class IpConfig {
    public HashSet<String> allowedIpSet = new HashSet<>();
    public IpConfig(@Value("${allowed.ips}") String[] allowedIps) {
        for (String ip : allowedIps) {
            System.out.println("white list ip: " + ip);
        }
        allowedIpSet.addAll(Arrays.asList(allowedIps));
    }
}

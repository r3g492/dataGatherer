package gwk.dataGatherer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class UploadController {
    private final AwsS3Service awsS3Service;

    @Value("${proxy.address}")
    String proxyAddress;

    @GetMapping("")
    public String viewUploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleUploadForm(Model model, String prefix,
                                   @RequestParam("file") MultipartFile multipart) {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null)
            ip = req.getRemoteAddr();

        /*if (!ipConfig.allowedIpSet.contains(ip)) {
            System.out.println("ip not allowed");
            System.out.println("ip: " + ip);
            return "notAllowed";
        }*/

        /*if (!ip.equals(proxyAddress)) {
            System.out.println("ip not allowed");
            System.out.println("ip: " + ip);
            return "notAllowed";
        }*/

        System.out.println("ip: " + ip);
        System.out.println("req.getRemoteAddr: " + req.getRemoteAddr());

        String fileName = multipart.getOriginalFilename();

        System.out.println("prefix: " + prefix);
        System.out.println("filename: " + fileName);

        String result = "";

        try {
            awsS3Service.uploadFile(fileName, multipart.getInputStream(), prefix);
            result = "File upload success";
        } catch (Exception ex) {
            result = "Error uploading file: " + ex.getMessage();
        }

        model.addAttribute("result", result);

        return "result";
    }
}

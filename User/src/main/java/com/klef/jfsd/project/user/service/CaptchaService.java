//package com.klef.jfsd.project.user.service;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class CaptchaService {
//
//    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
//    private static final String SECRET_KEY = "6LeR5pQqAAAAANVdl4nydwMMK86aPxFRNtJrCLlo"; // Your API key (Secret key)
//
//    public boolean verifyCaptcha(String captchaResponse) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        String url = RECAPTCHA_VERIFY_URL + "?secret=" + SECRET_KEY + "&response=" + captchaResponse;
//        CaptchaResponse response = restTemplate.postForObject(url, null, CaptchaResponse.class);
//
//        return response != null && response.isSuccess();
//    }
//
//    private static class CaptchaResponse {
//        private boolean success;
//
//        public boolean isSuccess() {
//            return success;
//        }
//
//        public void setSuccess(boolean success) {
//            this.success = success;
//        }
//    }
//}

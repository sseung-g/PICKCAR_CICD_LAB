package com.pickcar.filter;

import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
public class RequestWrapper extends ContentCachingRequestWrapper {

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    protected void loggingRequestAPI() throws IOException {
        String requestBody = getRequestContent();
        String queryString = this.getQueryString();
        StringBuilder uriBuilder = new StringBuilder(this.getRequestURI());
        if (queryString != null) {
            uriBuilder.append("?").append(queryString);
        }

        log.info("Request : {} URI : {} Content-Type=[{}] Payload=[{}]",
                this.getMethod(),
                uriBuilder,
                this.getContentType(),
                requestBody
        );
    }

    private String getRequestContent() {
        String requestBody = this.getContentAsString();
        String requestURI = this.getRequestURI();

        if(!requestURI.contains("/auth") || requestBody.isBlank()) {
            return requestBody;
        }

        return LogEncryptionHelper.encrypt(requestBody);
    }
}

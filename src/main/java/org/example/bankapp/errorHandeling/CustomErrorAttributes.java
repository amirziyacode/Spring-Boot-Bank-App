package org.example.bankapp.errorHandeling;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomErrorAttributes  implements ErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map <String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("status", 404);
        errorAttributes.put("message", "Page not found");
        errorAttributes.put("timestamp", System.currentTimeMillis());
        return errorAttributes;
    }

    @Override
    public Throwable getError(WebRequest webRequest) {
        return null;
    }
}

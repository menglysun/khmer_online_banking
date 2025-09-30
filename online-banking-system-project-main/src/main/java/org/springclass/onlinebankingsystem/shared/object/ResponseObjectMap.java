package org.springclass.onlinebankingsystem.shared.object;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ResponseObjectMap {

    public Map<String, Object> responseObject(Object obj, Long totalElements) {
        Map<String, Object> response = new HashMap<>();
        if (obj == null) {
            response.put("response", ResponseObject.error());
        }

        response.put("results", obj);
        response.put("length", totalElements);
        response.put("response", ResponseObject.success());
        return response;
    }

    public Map<String, Object> responseObject(Object obj, Long totalElements, int page, int size) {
        Map<String, Object> response = new HashMap<>();
        if (obj == null) {
            response.put("response", ResponseObject.error());
        }

        response.put("size", size);
        response.put("page", page);
        response.put("results", obj);
        response.put("length", totalElements);
        response.put("response", ResponseObject.success());
        response.put("totalPage", ((totalElements + size - 1) / size));
        return response;
    }

    public Map<String, Object> responseObject(Object obj) {
        Map<String, Object> response = new HashMap<>();
        if (obj == null) {
            response.put("response", ResponseObject.error());
        }
        response.put("results", obj);
        response.put("response", ResponseObject.success());

        log.info("response: {}", obj);
        return response;
    }

    public Map<String, Object> responseCodeWithMessage(int code, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("response", new ResponseObject(code, message));
        return response;
    }

    public Map<String, Object> responseCodeWithMessageAndObj(int code, String message, Object obj) {
        Map<String, Object> response = new HashMap<>();
        response.put("response", new ResponseObject(code, message));
        response.put("results", obj);
        return response;
    }
}

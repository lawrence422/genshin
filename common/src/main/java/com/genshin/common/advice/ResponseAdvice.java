package com.genshin.common.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.response.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@SuppressWarnings("rawtypes")
@RestControllerAdvice(basePackages = {"com.genshin.system"})
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(JsonResult.success(body));
            } catch (JsonProcessingException e) {
                return JsonResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            }
        } else if (body instanceof JsonResult) {
            return body;
        } else {
            return JsonResult.success(body);
        }
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult exception(Exception e){
        return JsonResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}

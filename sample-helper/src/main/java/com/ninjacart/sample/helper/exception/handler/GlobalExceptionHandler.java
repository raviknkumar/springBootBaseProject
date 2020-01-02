package com.ninjacart.sample.helper.exception.handler;


import com.ninjacart.sample.helper.exception.SampleException;
import com.ninjacart.sample.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice (basePackages = {"com.ninjacart"})
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SampleException.class)
    public @ResponseBody
    ApiResponse handleException(SampleException ex) {
        ApiResponse apiOutput = new ApiResponse();
        apiOutput.setSuccess(false);
        String errorCode = ex.getErrorCode();
        if (errorCode != null) {
            apiOutput.setErrorCode(ex.getErrorCode());
            apiOutput.setErrorMessage(ex.getErrorMessage());
        }
        log.error("Exception occured", ex);
        return apiOutput;
    }


}

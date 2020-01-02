package com.ninjacart.sample.helper.exception;


import com.ninjacart.sample.helper.exception.errorcode.ErrorCode;
import lombok.Data;

@Data
public class SampleException extends Exception {

    private String errorCode, errorMessage;

    public SampleException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode.name();
        this.errorMessage = errorCode.getErrorMessage();
    }

    public SampleException(ErrorCode errorCode, String... fields) {
        super(String.format(errorCode.getErrorMessage(), fields));
        this.errorCode = errorCode.name();
    }

    public SampleException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}

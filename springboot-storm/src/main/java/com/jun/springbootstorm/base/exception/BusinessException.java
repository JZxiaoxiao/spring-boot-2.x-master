package com.jun.springbootstorm.base.exception;

public class BusinessException extends GenericException {

    private static final long serialVersionUID = 6724107791583141581L;

    public BusinessException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BusinessException(String errorCode, String errorMessage ,Object data) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public BusinessException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public BusinessException(Exception oriEx) {
        super(oriEx);
    }

    public BusinessException(String message, Exception oriEx) {
        super(message, oriEx);
        this.errorMessage = message;
    }

}
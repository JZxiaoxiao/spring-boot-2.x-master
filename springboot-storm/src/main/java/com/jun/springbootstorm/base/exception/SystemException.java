package com.jun.springbootstorm.base.exception;

public class SystemException extends GenericException {

    private static final long serialVersionUID = 6753988275612054063L;

    /**
     * 系统异常构造器.<br>
     *
     * @param message
     *            异常信息
     * @ServiceMethod
     */
    public SystemException(String message) {
        super(message);
        this.errorMessage = message;
    }

    /**
     * 系统异常构造器.<br>
     *
     * @param errcode
     *            异常信息代码
     * @param message
     *            异常信息
     * @ServiceMethod
     */
    public SystemException(String errcode, String message) {
        super(message);
        this.errorCode = errcode;
        this.errorMessage = message;
    }

    /**
     * 系统异常构造器.<br>
     *
     * @param errcode
     *            异常信息代码
     * @param message
     *            异常信息
     * @param data
     *            异常数据
     * @ServiceMethod
     */
    public SystemException(String errcode, String message ,
                           Object data , String retryFlag) {
        super(message);
        this.errorCode = errcode;
        this.errorMessage = message;
        this.data = data;
        this.retryFlag = retryFlag;
    }

    /**
     * 系统异常构造器.<br>
     *
     * @param errcode
     *            异常信息代码
     * @param message
     *            异常信息
     * @param data
     *            异常数据
     * @ServiceMethod
     */
    public SystemException(String errcode, String message ,Object data) {
        super(message);
        this.errorCode = errcode;
        this.errorMessage = message;
        this.data = data;
    }


        /**
         * 系统异常构造器.<br>
         *
         * @param oriEx
         *            异常对象
         * @ServiceMethod
         */
    public SystemException(Exception oriEx) {
        super(oriEx);
    }

    /**
     * 系统异常构造器.<br>
     *
     * @param message
     *            异常信息
     * @param oriEx
     *            异常对象
     * @ServiceMethod
     */
    public SystemException(String message, Exception oriEx) {
        super(message, oriEx);
        this.errorMessage = message;
    }
}